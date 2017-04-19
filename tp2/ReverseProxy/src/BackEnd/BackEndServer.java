/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BackEnd;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import static java.lang.Thread.sleep;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class BackEndServer {
    public static void main(String args[]) throws Exception
   {
      //BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
      DatagramSocket clientSocket = new DatagramSocket();
      byte[] sendData = new byte[1024];
      byte[] receiveData = new byte[1024];
      InetAddress IPAddress = InetAddress.getByName("localhost");
      Beeper b=new Beeper(clientSocket,IPAddress);
      b.start();
      b.join();
      clientSocket.close();
   }
}
