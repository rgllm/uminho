
package client;

import java.io.Console;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Client {


    
    public static void main(String args[]){
         try{
             Socket requestSocket = null;
             try {
                 requestSocket = new Socket("10.1.1.1", 80);
             } catch (IOException iOException) {
                 System.out.println("Servidor desligado");
                 return;
             }
            ObjectOutputStream out = new ObjectOutputStream(requestSocket.getOutputStream());
            out.flush();
            ObjectInputStream in = new ObjectInputStream(requestSocket.getInputStream());
            
            //IMPRIMIR NO ECRAN MENSAGEM DE CONFRMACAO DO SERVIDOR
            String confirm=(String)in.readObject();
          
                System.out.println(confirm);
            if(confirm.startsWith("ERRO"))  
                return;

            //3: Communicating with the server
            String userInput="";
            do{
                Console console = System.console();
                userInput=console.readLine("Enviar mensagem:");
                out.writeObject(userInput);
                out.flush();
                String resposta=(String)in.readObject();
                System.out.println("Resposta do servidor: " + resposta );
                if(resposta.startsWith("ERRO"))  
                    return;
            }while(!userInput.equals("sair"));
            out.close();
            in.close();
            requestSocket.close();
        }
        catch(IOException | ClassNotFoundException ex){
            ex.printStackTrace();
        }
    }
}
