
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


class Notificator extends Thread{
    Socket cs;
    Sistema s;
    int userId;
    
    Notificator(Socket cs, Sistema s, int userId){
        this.cs=cs;
        this.s=s;
        this.userId=userId;
    }
    
    public void run(){
        try{
            PrintWriter out= new PrintWriter(cs.getOutputStream(),true);
            if(s.notifications.get(userId)==null)
                s.notifications.put(userId, new ArrayList<>());
            synchronized(s.notifications.get(userId)){
                while(true){
                    while(s.notifications.get(userId).isEmpty()){
                        System.out.println("notificador do user "+userId+" vai esperar");
                        s.notifications.get(userId).wait(); 
                        System.out.println("notificador do user "+userId+" foi acordado");
                    }
                    
                    out.println(":"+s.notifications.get(userId).get(0));
                    System.out.println("notificador do user "+userId+" escreveu para o cliente");
                    s.notifications.get(userId).remove(0);
                }
            }
        }catch(Exception e){e.printStackTrace();}
    }
}