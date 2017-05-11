package backendserver;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Server extends Thread {
    private ServerSocket tcpServerSocket ;
    private AtomicInteger nConnections;
    private Socket tcpClientSocket;
    
    public Server(ServerSocket ss , AtomicInteger nc , Socket c){
        tcpServerSocket = ss;
        nConnections = nc;
        tcpClientSocket = c;
    }
    
    public void run(){
        try{
            nConnections.incrementAndGet();
            String pedidoAux="quit";
            ObjectOutputStream out = new ObjectOutputStream(tcpClientSocket.getOutputStream());                                
            ObjectInputStream in = new ObjectInputStream(tcpClientSocket.getInputStream());
            
            /* Informs client of a successful connection*/
            out.writeObject("SERVER: Connection successful.");
                out.flush();
                
            do{
                /*Reads client request. */
                String pedido = (String)in.readObject();
                pedidoAux=pedido;
                if (pedido.equals("hello")){
                    out.writeObject("hello client");
                    out.flush();
                }else if(pedido.equals("quit")){
                    out.writeObject("BYE!");
                    out.flush();
                }
                else{
                    out.writeObject("I received your request but I did not understand.");
                    out.flush();
                }
            }while(!pedidoAux.equals("quit"));
            
            nConnections.set(nConnections.get()-1);
            out.close();
            in.close();
            tcpClientSocket.close();
            
            this.interrupt();
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
