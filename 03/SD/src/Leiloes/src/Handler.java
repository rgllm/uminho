
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


class Handler extends Thread {
    Socket cs;
    Sistema s;
    int userId; // se ==-1 não está logado

    public Handler(Socket cs,Sistema s){
        this.cs=cs;
        this.s=s;
    }

    public void run() {
        try{
            PrintWriter out= new PrintWriter(cs.getOutputStream(),true);
            BufferedReader in = new BufferedReader (new InputStreamReader(cs.getInputStream()));
            String current,op;
            ArrayList<String> leiloes;
            Notificator n=null;
            while((current=in.readLine())!=null){
                System.out.println(current);
                String arr[] = current.split(" ");
                switch(arr[0]){
                    case "login":
                        int userId=s.login(arr[1], arr[2]);
                        if(userId>0){
                            n=new Notificator(cs,s,userId);
                            n.start();
                        }
                        out.println(""+userId);

                        break;
                    case "getTipoUser":
                        out.println("" + s.getTipoUser(Integer.parseInt(arr[1])));
                        break;
                    case "licita":
                        out.println("" + s.licita(Integer.parseInt(arr[1]) , Integer.parseInt(arr[2]) , Float.parseFloat(arr[3])) ) ;
                        break;
                    case "addLeilao":
                        out.println("" + s.addLeilao( Integer.parseInt(arr[1]) ,
                                                    Float.parseFloat(arr[2]) ,
                                                    current.substring(arr[0].length()+arr[1].length()+arr[2].length()+3)
                                                    ) );
                        break;
                    case "addUser":
                        out.println(""+s.addUser(arr[1], arr[2], Integer.parseInt(arr[3])));
                        break;
                    case "getLeiloes":
                        leiloes=s.getLeiloes(Integer.parseInt(arr[1]));
                        out.println(""+ leiloes.size());
                        for(String leilao : leiloes)
                            out.println(leilao);
                        break;
                    case "terminaLeilao":
                        DuploIntFloat dup=s.terminaLeilao(Integer.parseInt(arr[1]),Integer.parseInt(arr[2]));
                        if(dup==null) out.println("-1");
                        else{
                            out.println(""+dup.idVencedor);
                            System.out.println("têm que ser notificados os utilizadores com os seguintes ids: ");
                            // if(dup.idVencedor==0) não houve licitações
                            for(int x : dup.ids){
                                if(s.notifications.get(x)==null)
                                    s.notifications.put(x, new ArrayList<>());
                                synchronized(s.notifications.get(x)){
                                    s.notifications.get(x).add("O vencedor do leilão "+Integer.parseInt(arr[2])
                                                               + " foi o utilizador "+ s.getUser(dup.idVencedor).username
                                                               + " com uma licitação de " + dup.valorFecho + "€");
                                    s.notifications.get(x).notifyAll();
                                    System.out.println(""+x);
                                }
                            }
                        }
                        break;
                    case "logout":
                        if(n!=null)n.stop();
                        break;
                    case "kill":
                        stop();
                        break;
                }
            }
            in.close();
            out.close();
            cs.close();
        }catch(Exception e){e.printStackTrace();}
    }
}