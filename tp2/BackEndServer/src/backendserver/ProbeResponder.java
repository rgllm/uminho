
package backendserver;

import Utils.PDU;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.*;



public class ProbeResponder extends Thread {
    DatagramSocket cs;
    InetAddress ip;
    
    public ProbeResponder(DatagramSocket clientSocket,InetAddress ipAddress){
       cs=clientSocket;
       ip=ipAddress;
    }
    
    public void run(){
        int seq;
        int nConexoes;
        while(true){
            seq=recebePedido();
            nConexoes=0; //NOTA: so pode ser feito na fase 2
            if(seq==-1){
                System.out.println("PR: Recebida mensagem com sintaxe desconhecida");
                // reponder alguma coisa?
            }
            else{
                responde(new PDU(seq,nConexoes,ip));
            }
        }
    }
    // responde(string resposta) retorna true em caso de sucesso ou falso caso contrário 
    private boolean responde(PDU resposta){
        byte[] data;
        data = new byte[1024];
        try {
            Serializer s=new Serializer();
            data =s.serialize(resposta);
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
        data = new byte[1024];
        DatagramPacket pacote = new DatagramPacket(data, data.length);
        
        try {
            cs.receive(pacote);
            // FALTA: Certificar que o pacote vem do reverse proxy e que a mensagem tem a sintaxe correta
            String pedido=new String(pacote.getData());
            System.out.println("PR: recebi um probe request :\n\t\t\t"+pedido);
            int seq=Integer.parseInt(pedido.split(" ")[1]);
            return seq;
        } catch (IOException ex) {
            ex.printStackTrace();
            // o que retornar nesta situação?
            return recebePedido();
        }
    }
    
}
