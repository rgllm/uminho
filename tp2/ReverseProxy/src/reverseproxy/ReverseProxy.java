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
    public static void main(String args[]) throws Exception
      {
            DatagramSocket serverSocket = new DatagramSocket(5555);
            HashMap<String,BackendInfo> infoBackends= new HashMap<>();  // a cada ip está associado um BackendInfo
            Listener l=new Listener(infoBackends,serverSocket);
            ProbeSenderString ps=new ProbeSenderString(infoBackends,serverSocket);
            l.start();
            ps.start();
            l.join();
            ps.join();
               
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
