/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reverseproxy;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReverseProxy {
    HashMap<String,BackendInfo> infoBackends= new HashMap<>();
    
    public static void main(String args[]) throws Exception
      {
            DatagramSocket serverSocket = new DatagramSocket(5555);
            byte[] receiveData = new byte[1024];
            byte[] sendData = new byte[1024];
            
            while(true)
               {
                  DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                  serverSocket.receive(receivePacket);
                  String sentence = new String( receivePacket.getData());
                  System.out.println("RECEIVED: " + sentence + "\n" + 
                                     "socket:" + receivePacket.getSocketAddress()+ "\n");
                  InetAddress IPAddress = receivePacket.getAddress();
                  int port = receivePacket.getPort();
                  String capitalizedSentence = sentence.toUpperCase();
                  sendData = capitalizedSentence.getBytes();
                  DatagramPacket sendPacket =new DatagramPacket(sendData, sendData.length, IPAddress, port);
                  //serverSocket.send(sendPacket);
               }
            // inicializar uma estrutura de dados para guardar dados estatisticos das conexões
            // que vão ser úteis para a escolha do servidor backend ao qual enviar o pedido
            
            // uma thread para ver que servidores estão disponíveis e
            // registar essa informação numa lista (endereço, porta, timestamp);
            
            // uma segunda thread que, de X em X milissegundos e
            // para cada servidor disponível na lista faz:
            //      regista hora atual
            //      envia um pedido de probing
            //      espera resposta ao pedido ( tem que ser definido um timeout)
            //      if( ! timeout && resposta pretendida ){
            //          regista hora atual
            //          calcula e regista round trip time
            //          regista/atualiza informação na estrutura de dados definida
            //      }
      }
    
}
