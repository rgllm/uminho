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
import static java.lang.Thread.sleep;
import java.util.Random;


class ProbeSenderString extends Thread{
    HashMap<String,BackendInfo> infoBackends;
    DatagramSocket serverSocket;
    public ProbeSenderString(HashMap<String,BackendInfo> ib , DatagramSocket ss){
        infoBackends=ib;
        serverSocket=ss;
    }
    public void run(){
        try{
            
            while(true){
                sleep(1000);
                synchronized(infoBackends){
                    for(BackendInfo beServer : infoBackends.values()){
                        byte[] sendData = new byte[1024];
                        
                        System.out.println("PS: vou enviar probe request para o "+beServer.getIpString()+"\n");
                        InetAddress IPAddress = beServer.getIp();
                        int seq=new Random().nextInt();
                        sendData = ("Probe: "+seq).getBytes();
                        DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length, IPAddress, 5555);
                        serverSocket.send(sendPacket);
                        Date antes = new Date();
                        PDU resposta=new PDU(-1,-1,InetAddress.getByName("localhost")); // acho que e preciso mudar isto
                        DatagramPacket receivePacket ;
                        boolean timeout=false;
                        do{
                            byte[] receiveData = new byte[1024];
                            receivePacket = new DatagramPacket(receiveData, receiveData.length);
                            serverSocket.receive(receivePacket);
                            receiveData=receivePacket.getData();
                            String message=new String(receiveData);
                            System.out.println("PS: recebi um pacote: "+ message+"\n");
                            if(!(message.startsWith("HELLO") || message.startsWith("Probe"))){
                                String []arr=message.split(",");
                                int nSeq= Integer.parseInt(arr[0]);
                                int nConexoes= Integer.parseInt(arr[0]);
                                // se calhar vai ser preciso manipular a string do IP antes de meter no PDU
                                resposta = new PDU(nSeq,nConexoes,receivePacket.getAddress());
                            }
                            if((new Date().getTime() - antes.getTime()) > 2000){
                                timeout=true;
                                break;
                            }
                        }while(resposta.getSeq()!=seq);
                        if(!timeout){
                            float rtt=new Date().getTime() - antes.getTime();
                            infoBackends.get(beServer).atualizaRTT(rtt);
                            infoBackends.get(beServer).setConexoesAtivas(resposta.getnConexoes());
                            System.out.println("PS: recebi resposta do " + beServer + "\n\trtt: " + rtt + " ; numero de conexoes ativas : " + resposta.getnConexoes()+"\n" );
                        }
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }       
}
