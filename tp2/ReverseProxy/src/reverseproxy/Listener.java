/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;


class Listener extends Thread{
    DatagramSocket serverSocket;
    HashMap<String,BackendInfo> infoBackends;
    public Listener(HashMap<String,BackendInfo> ib , DatagramSocket ss){
        infoBackends=ib;
        serverSocket=ss;   
    }
    public void run(){
        while(true){
            try {
                sleep(3000);
                byte[] data = new byte[1024];
                printInfoBackends();
                DatagramPacket pacote = new DatagramPacket(data, data.length);
                serverSocket.receive(pacote);
                InetAddress ia=pacote.getAddress();
                String address=ia.toString().substring(1);
                String message=new String(pacote.getData());
                System.out.println("LISTENER recebeu : '" +message+"'");
                if(message.startsWith("HELLO") && !infoBackends.containsKey(address)){
                    BackendInfo bi=new BackendInfo(ia);
                    System.out.println("LISTENER: vai adicionar um novo backend à tabela -- "+address);
                    synchronized(infoBackends){
                        infoBackends.put(address,bi);
                    }
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void printInfoBackends(){
        System.out.println("LISTENER : infoBackends ");
        for(BackendInfo bi : infoBackends.values())
            System.out.println("\tip:"+bi.getIpString()+" #medicoes:"+bi.getMedicoesRTT()+" somaRTTs:"+bi.getSomaRTTs()+" #conexoesAtivas:"+bi.getConexoesAtivas());
        System.out.println("");
    }
}
