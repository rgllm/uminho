
package BackEnd;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ProbeResponder extends Thread {
    byte[] data;
    DatagramSocket cs;
    InetAddress ip;
    
    public ProbeResponder(DatagramSocket clientSocket,InetAddress ipAddress){
       cs=clientSocket;
       data = new byte[1024];
       ip=ipAddress;
    }
    
    public void run(){
        String pedido=recebePedido();
        if(pedido.equals("diz-me coisas"))
            responde("sou o "+ip.toString()+" e tenho <x> conexões ativas.");
        else{
            System.out.println("Recebida mensagem com sintaxe desconhecida");
            // reponder alguma coisa?
        }
    }
    // responde(string resposta) retorna true em caso de sucesso ou falso caso contrário 
    private boolean responde(String resposta){
        try {
            data = resposta.getBytes();
            DatagramPacket pacote =new DatagramPacket(data, data.length, ip, 5555);
            cs.send(pacote);
            return true;
        } catch (IOException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    private String recebePedido(){
        DatagramPacket pacote = new DatagramPacket(data, data.length);
        
        try {
            cs.receive(pacote);
            return new String(pacote.getData());
        } catch (IOException ex) {
            ex.printStackTrace();
            // o que retornar nesta situação?
            return recebePedido();
        }
    }
    
}
