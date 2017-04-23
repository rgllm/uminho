/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.*;
import java.util.Random;


class ProbeSender extends Thread{
    HashMap<String,BackendInfo> infoBackends;
    DatagramSocket serverSocket;
    public ProbeSender(HashMap<String,BackendInfo> ib , DatagramSocket ss){
        infoBackends=ib;
        serverSocket=ss;
    }
    public void run(){
        try{
            byte[] data = new byte[1024];
            while(true){
                sleep(1000);
                synchronized(infoBackends){
                    for(String beServer : infoBackends.keySet()){
                        System.out.println("PS: vou enviar probe request para o "+beServer);
                        InetAddress IPAddress = InetAddress.getByName(beServer);
                        int seq=new Random().nextInt();
                        data = ("Probe: "+seq).getBytes();
                        DatagramPacket sendPacket =new DatagramPacket(data, data.length, IPAddress, 5555);
                        PDU resposta=new PDU(-1,-1,InetAddress.getByName("localhost")); // acho que e preciso mudar isto
                        DatagramPacket receivePacket = new DatagramPacket(data, data.length);
                        boolean timeout=false;
                        serverSocket.send(sendPacket);
                        Date antes = new Date();
                        do{
                            receivePacket = new DatagramPacket(data, data.length);
                            serverSocket.receive(receivePacket);
                            Serializer s= new Serializer();
                            System.out.println("----------------------"+resposta.getSeq());
                            data=receivePacket.getData();
                            String message=new String(data);
                            if(!(message.equals("HELLO") || message.startsWith("Probe")))
                                resposta = (PDU)s.deserialize(data);
                            if((new Date().getTime() - antes.getTime()) > 2000){
                                timeout=true;
                                break;
                            }
                        }while(resposta.getSeq()!=seq);
                        if(!timeout){
                            float rtt=new Date().getTime() - antes.getTime();
                            infoBackends.get(beServer).atualizaRTT(rtt);
                            infoBackends.get(beServer).setConexoesAtivas(resposta.getnConexoes());
                            System.out.println("PS: recebi resposta do " + beServer + "\n\trtt: " + rtt + " ; numero de conexoes ativas : " + resposta.getnConexoes() );
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }       
}
