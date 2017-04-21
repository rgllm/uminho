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
                        InetAddress IPAddress = InetAddress.getByName(beServer);
                        data = "Probe: ".getBytes();
                        DatagramPacket sendPacket =new DatagramPacket(data, data.length, IPAddress, 5555);
                        String resposta="";
                        DatagramPacket receivePacket = new DatagramPacket(data, data.length);
                        boolean timeout=false;
                        serverSocket.send(sendPacket);
                        Date antes = new Date();
                        while(!( resposta.startsWith("sou o") && receivePacket.getAddress().equals(IPAddress) )){
                            receivePacket = new DatagramPacket(data, data.length);
                            serverSocket.receive(receivePacket);
                            resposta = new String( receivePacket.getData());
                            if((new Date().getTime() - antes.getTime()) > 2000){
                                timeout=true;
                                break;
                            }
                        }
                        if(!timeout){
                            float rtt=new Date().getTime() - antes.getTime();
                            infoBackends.get(beServer).atualizaRTT(rtt);
                            // analisar a resposta para atualizar o numero de conexões ativas
                            System.out.println(resposta);
                        }

                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }       
}
