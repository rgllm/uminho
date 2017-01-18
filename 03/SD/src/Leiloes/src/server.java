import java.net.*;
import java.io.*;
import java.util.*;

class Server{
    public static void main(String [] args){
        try{
            ServerSocket ss= new ServerSocket(9999);
            Sistema s=new Sistema();
            while(true){
                Socket cs =ss.accept();
                Thread t = new Handler(cs,s);
                t.start();    
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
