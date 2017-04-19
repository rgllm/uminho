/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.HashMap;


class Listener extends Thread{
    DatagramSocket serverSocket;
    HashMap<String,BackendInfo> infoBackends;
    public Listener(HashMap<String,BackendInfo> ib , DatagramSocket ss){
        infoBackends=ib;
        serverSocket=ss;   
    }
    public void run(){
        byte[] data = new byte[1024];
        while(true){
            try {
                DatagramPacket pacote = new DatagramPacket(data, data.length);
                serverSocket.receive(pacote);
                String address=pacote.getAddress().toString().substring(1);
                String message=new String(pacote.getData());
                System.out.println(message);
                if(message.equals("HELLO") && !infoBackends.containsKey(address))
                    System.out.println("----");
                    synchronized(infoBackends){
                        infoBackends.put(address,new BackendInfo(pacote.getAddress()));
                    }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
