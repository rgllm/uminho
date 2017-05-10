/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backendserver;


import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;


public class BackEndServer {
    public static void main(String args[]){
        try {
            //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket udpServerSocket = new DatagramSocket(5555);
            ServerSocket tcpServerSocket = new ServerSocket(80);
            AtomicInteger nConexoes=new AtomicInteger(0);
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            InetAddress IPAddress = InetAddress.getByName("10.1.1.1");
            
            Beeper b=new Beeper(udpServerSocket,IPAddress);
            MonitorUDP m= new MonitorUDP(udpServerSocket,IPAddress,nConexoes);
            b.start();
            m.start();
            
            while(true){
                Socket client=tcpServerSocket.accept();
                Server s= new Server(tcpServerSocket,nConexoes,client);
                s.start();
                
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
   }
}
