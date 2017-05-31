package reverseproxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ConcurrentHashMap;


class Listener extends Thread{
    private UDPServerSocket serverSocket;
    private ConcurrentHashMap<String,BackendInfo> infoBackends;
    private Pacote probeResponse;
    
    
    public Listener(ConcurrentHashMap<String,BackendInfo> ib , UDPServerSocket ss , Pacote pr){
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
                    if( !infoBackends.containsKey(address)){
                        BackendInfo bi=new BackendInfo(ia);
                        System.out.println("LISTENER: add new backend to the table -- "+address+ "\n");
                        infoBackends.put(address,bi);  // TODO: devia meter synchronized aqui, mas da DeadLock
                    }
                }
                else if(message.trim().matches("[0-9]+,[0-9]+")){
                    synchronized(probeResponse){
                        probeResponse.setPacote(pacote);
                        probeResponse.notifyAll();
                    }
                }
                else System.out.println("LISTENER: received a message with unknown syntax: "+message+ "\n");
                
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
