package reverseproxy;

import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.ConcurrentHashMap;

public class ReverseProxy {    
    public static void main(String args[]) throws Exception
      {
            UDPServerSocket udpServerSocket = new UDPServerSocket();
            ConcurrentHashMap<String,BackendInfo> infoBackends= new ConcurrentHashMap<>();  // a cada ip está associado um BackendInfo
            ServerSocket tcpServerSocket = new ServerSocket(80);
            byte []data=new byte[0];
            Pacote probeResponse=new Pacote(new DatagramPacket(data, data.length, InetAddress.getByName("0.0.0.0"), 1000));
            Listener l=new Listener(infoBackends,udpServerSocket,probeResponse);
            ProbeSender ps=new ProbeSender(infoBackends,udpServerSocket,probeResponse);
            l.start();
            ps.start();
            
            while(true){
                Socket clientSocket=tcpServerSocket.accept();
                System.out.println("Reverse: pedido de conexao do "+ clientSocket.getInetAddress().getHostAddress());
                if(!infoBackends.isEmpty()){
                    ArrayList<BackendInfo> backends=new ArrayList<>(infoBackends.values());
                    Collections.sort(backends,new Comparator<BackendInfo>() {
                        @Override
                        public int compare(BackendInfo b1, BackendInfo b2) {
                            if(b1.getTaxaPerdas() < b2.getTaxaPerdas())
                                return -1;
                            if(b1.getTaxaPerdas()> b2.getTaxaPerdas())
                                return 1;

                            if(b1.getConexoesAtivas() < b2.getConexoesAtivas())
                                return -1;
                            if(b1.getConexoesAtivas() > b2.getConexoesAtivas())
                                return 1;

                            if(b1.getMediaRTT() < b2.getMediaRTT())
                                return -1;
                            if(b1.getMediaRTT() > b2.getMediaRTT())
                                return 1;

                            return 0;
                        }
                    });
                
                    BackendInfo melhor=backends.get(0);
                    System.out.println("Reverse: o melhor backend e o "+ melhor.getIpString());
                    Handler h=new Handler(clientSocket,melhor);
                    h.start();
                }
                else{
                    ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                    out.writeObject("ERRO: De momento nao ha servidores disponiveis");
                    out.flush();

                }
            }

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
