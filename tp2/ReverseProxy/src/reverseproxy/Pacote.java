package reverseproxy;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Pacote {
    private DatagramPacket pacote;
    
    public Pacote(DatagramPacket p){
        pacote=p;
    }
    
    synchronized public DatagramPacket getPacote() {
        return pacote;
    }

    synchronized public void setPacote(DatagramPacket pacote) {
        this.pacote = pacote;
    }
    
    synchronized public void reset(){
        try {
            byte []data=new byte[0];
            pacote=new DatagramPacket(data, data.length, InetAddress.getByName("0.0.0.0"), 1000);
        } catch (UnknownHostException ex) {
            ex.printStackTrace();
        }
    }
    
    
}
