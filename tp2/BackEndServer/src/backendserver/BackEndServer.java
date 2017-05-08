/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backendserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BackEndServer {
    public static void main(String args[]){
        try {
            //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            DatagramSocket beSocket = new DatagramSocket(5555);
            byte[] sendData = new byte[1024];
            byte[] receiveData = new byte[1024];
            InetAddress IPAddress = InetAddress.getByName("10.1.1.1");
            Beeper b=new Beeper(beSocket,IPAddress);
            MonitorUDP m= new MonitorUDP(beSocket,IPAddress);
            b.start();
            m.start();
            b.join();
            m.join();
            beSocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
   }
}
