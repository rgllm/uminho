package backendserver;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class BackEndServer {
    public static final String serverAdress = "10.1.1.1";
    
    public static void main(String args[]){
        try {
            DatagramSocket udpServerSocket = new DatagramSocket(5555);
            ServerSocket tcpServerSocket = new ServerSocket(80);
            AtomicInteger nConnections=new AtomicInteger(0);
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            InetAddress IPAddress = InetAddress.getByName(serverAdress);
            
            Beeper b=new Beeper(udpServerSocket,IPAddress);
            MonitorUDP m= new MonitorUDP(udpServerSocket,IPAddress, nConnections);
            b.start();
            m.start();
            
            while(true){
                Socket client=tcpServerSocket.accept();
                Server s= new Server(tcpServerSocket, nConnections,client);
                s.start();
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
   }
}
