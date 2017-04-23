
package backendserver;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Beeper extends Thread {
   byte[] data;
   DatagramSocket clientSocket;
   InetAddress ipAddress;
   
   public Beeper(DatagramSocket cs,InetAddress ip){
       clientSocket=cs;
       data = new byte[1024];
       ipAddress=ip;
   }
    public void run(){
        while(true){
            try {
                data = "HELLO".getBytes();
                DatagramPacket sendPacket = new DatagramPacket(data, data.length, ipAddress, 5555);
                clientSocket.send(sendPacket);
                sleep(7000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
      } 
    }
}
