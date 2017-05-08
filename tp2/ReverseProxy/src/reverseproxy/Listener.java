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
    private UDPServerSocket serverSocket;
    private HashMap<String,BackendInfo> infoBackends;
    private Pacote probeResponse;
    public Listener(HashMap<String,BackendInfo> ib , UDPServerSocket ss , Pacote pr){
        infoBackends=ib;
        serverSocket=ss;   
        probeResponse=pr;
    }
    public void run(){
        while(true){
            try {
                byte[] data = new byte[1024];
                printInfoBackends();
                DatagramPacket pacote = serverSocket.receberPacote();
                InetAddress ia=pacote.getAddress();
                String address=ia.toString().substring(1);
                String message=new String(pacote.getData());
                System.out.println("LISTENER recebeu : '" +message+"' \n");
                if(message.trim().equals("HELLO")){
                    synchronized(infoBackends){
                        if( !infoBackends.containsKey(address)){
                            BackendInfo bi=new BackendInfo(ia);
                            System.out.println("LISTENER: vai adicionar um novo backend à tabela -- "+address+ "\n");
                            infoBackends.put(address,bi);  // devia meter synchronized aqui, mas da DeadLock
                        }
                    }
                }
                else if(message.trim().matches("[0-9]+,[0-9]+")){
                    synchronized(probeResponse){
                        probeResponse.setPacote(pacote);
                        probeResponse.notifyAll();
                    }
                }
                else System.out.println("LISTENER: recebida mensagem com sintaxe desconhecida: "+message+ "\n");
                
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
