
package reverseproxy;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ServerSocket {
    private DatagramSocket serverSocket;
    
    public ServerSocket(){
        try {
            serverSocket=new DatagramSocket(5555);
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
    }
    
    synchronized DatagramPacket receberPacote() throws IOException{
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = null;
        receivePacket = new DatagramPacket(receiveData, receiveData.length);
        serverSocket.receive(receivePacket);
        return receivePacket;
    }
    
    synchronized void enviarPacote(DatagramPacket pacote) throws IOException{
        serverSocket.send(pacote);
    }
}
