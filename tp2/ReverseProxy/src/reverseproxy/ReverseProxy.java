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
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReverseProxy {    
    public static void main(String args[]) throws Exception
      {
            UDPServerSocket udpServerSocket = new UDPServerSocket();
            HashMap<String,BackendInfo> infoBackends= new HashMap<>();  // a cada ip está associado um BackendInfo
            ServerSocket tcpServerSocket = new ServerSocket(80);
            byte []data=new byte[0];
            Pacote probeResponse=new Pacote(new DatagramPacket(data, data.length, InetAddress.getByName("0.0.0.0"), 1000));
            Listener l=new Listener(infoBackends,udpServerSocket,probeResponse);
            ProbeSender ps=new ProbeSender(infoBackends,udpServerSocket,probeResponse);
            l.start();
            ps.start();
            l.join();
            ps.join();
               
            
            // --------------------------- FASE 1 -------------------------------------
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
            
            
            // --------------------------- FASE 2 -------------------------------------
            // while(true){
            //     aceitar conexao
            //     encontrar melhor backend
            //     criar thread com informacao sobre o cliente e o backend em questao,
            //     que vai intermediar a conversa (sera preciso exclusao mutua no socket do lado do cliente?)
            //     a thread deve matar-se a si propria assim que a conversa terminar
            // }
            
            
      }
    
}
