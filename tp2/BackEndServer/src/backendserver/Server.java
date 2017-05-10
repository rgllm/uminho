/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backendserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Server extends Thread {
    private ServerSocket tcpServerSocket ;
    private AtomicInteger nConexoes;
    private Socket clientSocket;
    
    public Server(ServerSocket ss , AtomicInteger nc , Socket c){
        tcpServerSocket = ss;
        nConexoes = nc;
        clientSocket = c;
    }
    
    public void run(){
        try{
            nConexoes.incrementAndGet();
            String pedidoAux="sair";
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());                                
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            
            //INFORMAR O CLIENTE DO SUCESSO DA CONEXAO
            out.writeObject("SERVER: Connection successful");
                out.flush();
                
            do{
                //LER PEDIDO DO CLIENTE
                String pedido = (String)in.readObject();
                pedidoAux=pedido;
                if (pedido.equals("ola")){
                    out.writeObject("ola pa");
                    out.flush();
                }else if(pedido.equals("sair")){
                    out.writeObject("Adeus");
                    out.flush();
                }
                else{
                    out.writeObject("nao percebi");
                    out.flush();
                }
            }while(!pedidoAux.equals("sair"));
            
            nConexoes.set(nConexoes.get()-1);
            out.close();
            in.close();
            clientSocket.close();
            
            this.interrupt();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
