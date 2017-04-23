
package backendserver;

import Utils.PDU;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import Utils.*;



public class ProbeResponderString extends Thread {
    DatagramSocket cs;
    InetAddress ip;
    
    public ProbeResponderString(DatagramSocket clientSocket,InetAddress ipAddress){
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
                while(!responde(""+seq+","+nConexoes)){};
            }
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
        } catch (IOException ex) {
            ex.printStackTrace();
            // o que retornar nesta situação?
        }
        return recebePedido();

    }
    
}
