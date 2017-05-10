
package backendserver;

import Utils.PDU;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.*;
import java.util.concurrent.atomic.AtomicInteger;



public class MonitorUDP extends Thread {
    DatagramSocket cs;
    InetAddress ip;
    AtomicInteger nConexoes;
    
    public MonitorUDP(DatagramSocket clientSocket,InetAddress ipAddress , AtomicInteger nc){
       cs=clientSocket;
       ip=ipAddress;
       nConexoes=nc;
    }
    
    public void run(){
        int seq;
        while(true){
            seq=recebePedido();
            if(seq==-1)
                System.out.println("PR: Recebida mensagem com sintaxe desconhecida");
            else if(seq==-2)
                System.out.println("PR: Erro a receber pacote ou o reverseProxy esta desligado");
            responde(""+seq+","+nConexoes.toString());
        }
    }
    // responde(string resposta) retorna true em caso de sucesso ou falso caso contrário 
    private boolean responde(String resposta){
        byte[] data;
        data = new byte[1024];
        try {
            data=resposta.getBytes();
            DatagramPacket pacote =new DatagramPacket(data, data.length, ip, 5555);
            cs.send(pacote);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private int recebePedido(){
        byte[] data;
        int seq;
        data = new byte[1024];
        DatagramPacket pacote = new DatagramPacket(data, data.length);
        
        try {
            cs.receive(pacote);
            // FALTA: Certificar que o pacote vem do reverse proxy e que a mensagem tem a sintaxe correta
            String pedido=new String(pacote.getData());
            if(pedido.startsWith("Probe: ")){
                System.out.println("PR: recebi um probe request :\n\t\t\t"+pedido);
                String []arr=pedido.split(" ");
                seq=Integer.parseInt(arr[1].trim());
                return seq;
            }
            else return-1;
        } catch (IOException ex) {
            ex.printStackTrace();
            return -2;
        }

    }
    
}
