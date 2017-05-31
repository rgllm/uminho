package reverseproxy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class Handler extends Thread {
    private Socket clientSocket;
    private BackendInfo backend;
    
    public Handler(Socket cs,BackendInfo backend){
        clientSocket=cs;
        this.backend=backend;
    }
    
    public void run(){
        ObjectOutputStream aux=null;
        try {
            ObjectOutputStream clientOut = new ObjectOutputStream(clientSocket.getOutputStream());
            aux=clientOut;
            ObjectInputStream clientIn = new ObjectInputStream(clientSocket.getInputStream());
            
            //CRIAR UM NOVO SOCKET LIGADO AO BACKEND SERVER
            Socket backendSocket = new Socket(backend.getIpString(), 80);
            ObjectOutputStream backendOut = new ObjectOutputStream(backendSocket.getOutputStream());
            ObjectInputStream backendIn = new ObjectInputStream(backendSocket.getInputStream());

            //LER CONFIRMACAO DA CONEXAO DO BACKEND
            String confirm=(String)backendIn.readObject();
            
            //RETRANSMITIR DO BACKEND PARA O CLIENTE
            clientOut.writeObject(confirm);
            clientOut.flush();
            
            String mensagem="";
            do{
                // LER PEDIDO DO CLIENTE
                mensagem = (String)clientIn.readObject();
                System.out.println("+++++++++++"+ mensagem);

                //RETRANSMITIR PEDIDO DO CLIENTE PARA O BACKEND SERVER
                backendOut.writeObject(mensagem);
                backendOut.flush();

                //LER RESPOSTA DO BACKEND
                String resposta=(String)backendIn.readObject();

                //RETRANSMITIR RESPOSTA DO BACKEND PARA O CLIENTE
                clientOut.writeObject(resposta);
                clientOut.flush();
                
            }while(!mensagem.trim().equals("quit"));
            
            backendOut.close();
            backendIn.close();
            backendSocket.close();
            
            clientOut.close();
            clientIn.close();
            clientSocket.close();

            
            this.interrupt();
            
        } catch (IOException | ClassNotFoundException ex) {
            try {
                aux.writeObject("ERRO: Fail trying to connect with server.");
                aux.flush();

            } catch (IOException ex1) {
                ex.printStackTrace();
            }
            ex.printStackTrace();
        }   
    }
}
