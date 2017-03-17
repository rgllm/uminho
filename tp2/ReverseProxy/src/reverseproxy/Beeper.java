/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Beeper extends Thread {
   boolean ocupado;
   byte[] sendData;
   DatagramSocket clientSocket;
   InetAddress ipAddress;
   
   public Beeper(boolean oc, DatagramSocket cs,InetAddress ip){
       ocupado=oc;
       clientSocket=cs;
       sendData = new byte[1024];
       ipAddress=ip;
   }
    public void run(){
        while(!ocupado){
            try {
                sendData = "HELLO".getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAddress, 5555);
                clientSocket.send(sendPacket);
                sleep(1000);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
      } 
    }
}
