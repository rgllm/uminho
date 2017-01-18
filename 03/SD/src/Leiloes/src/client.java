import java.net.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Client{

/*
 * Função que retorna true caso a String s seja um número
 * e false caso a String s não seja um número.
*/
    public static boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d+");
    }

/*
 * Função MAIN
*/
    public static void main(String[] args){
        String current,username, password;
        int opcao, opcao2,userId,tipo;
        float valor;
        try{
            Socket cs = new Socket("127.0.0.1",9999); //Porta 9999
            PrintWriter out= new PrintWriter(cs.getOutputStream(),true);
            BufferedReader in = new BufferedReader (new InputStreamReader(cs.getInputStream()));
            BufferedReader sin = new BufferedReader (new InputStreamReader(System.in));
            ClientReader r=new ClientReader(in);
            r.start();
            do{
                printMenu1();
                current=sin.readLine();
                while(isNumeric(current)==false){
                    current=sin.readLine();
                }
                opcao=Integer.parseInt(current);
                if(opcao==1){
                    System.out.println("\n\n\n");
                    System.out.println("Username: ");
                    username=sin.readLine();
                    System.out.println("\nPassword: ");
                    password=sin.readLine();
                    if((userId=send("login " + username + " " + password, out,r))!=-1){
                        System.out.println("\n\n\n");
                        System.out.println("You successfully logged in with ID "+ userId);
                        if((tipo=send("getTipoUser " + userId,out,r))==1){ // menu comprador
                            do{
                                printMenu2Buyer();
                                current=sin.readLine();
                                 while(isNumeric(current)==false){
                                    current=sin.readLine();
                                }
                                opcao2=Integer.parseInt(current);
                                if(opcao2==1){
                                    System.out.println("\n\n\n");
                                    printLeiloes(userId,out,r);
                                }
                                // int sellerId, float base_price,String item_description
                                else if(opcao2==2){
                                    System.out.println("\n\n\n");
                                    int id_leilao;
                                    System.out.println("Auction ID: ");
                                    id_leilao=Integer.parseInt(sin.readLine());
                                    System.out.println("Bid Value: ");
                                    valor=Float.parseFloat(sin.readLine());
                                    int res=send("licita " + userId + " " + id_leilao + " " + valor, out, r);
                                    if(res==0)
                                        System.out.println("There isn't any auction with that ID!");
                                    else if(res==-1)
                                        System.out.println("Bid value is lower than the base price.");
                                    else if(res==-2)
                                        System.out.println("The bid must have a value higher than the highest bid amount so far!");
                                    else System.out.println("Your bid was registered with success!");
                                }
                            }while(opcao2!=0);
                            out.println("logout");
                        }
                        else if(tipo==2){ // menu vendedor
                            do{
                                printMenu2Seller();
                                current=sin.readLine();
                                //falta verificar se current é um numero antes de fazer parseInt
                                opcao2=Integer.parseInt(current);
                                if(opcao2==1){
                                    System.out.println("\n\n\n");
                                    printLeiloes(userId,out,r);
                                }
                                else if(opcao2==2){
                                    System.out.println("\n\n\n");
                                    System.out.println("Description: ");
                                    current=sin.readLine();
                                    System.out.println("Base Price: ");
                                    valor=Float.parseFloat(sin.readLine());
                                    //falta analisar return da funçao
                                    send("addLeilao " + userId + " " +  valor + " " +  current, out,r);
                                    System.out.println("Auction added!");
                                }
                                else if(opcao2==3){
                                    System.out.println("\n\n\n");
                                    int id_leilao;
                                    System.out.println("Auction ID: ");
                                    id_leilao=Integer.parseInt(sin.readLine());
                                    int res=send("terminaLeilao " + userId + " " +  id_leilao , out,r);
                                    if(res==-1)
                                        System.out.println("This auction was not registered by you or does not exist.");
                                    else
                                        System.out.println("You ended the auction with success.");
                                }
                            }while(opcao2!=0);
                            out.println("logout");
                        }
                        else System.out.println("There was an error in your user type.");

                    }
                    else{
                        System.out.println("\n\n\n");
                        System.out.println("Your username or password is wrong.");
                    }
                }
                else if(opcao==2){
                    System.out.println("\n\n\n");
                    System.out.println("Username: ");
                    username=sin.readLine();
                    System.out.println("Password: ");
                    password=sin.readLine();
                    System.out.println("Type (1-> Buyer, 2-> Seller): ");
                    current=sin.readLine();
                    while(isNumeric(current)==false){
                        current=sin.readLine();
                    }
                    tipo=Integer.parseInt(current);
                    while (tipo!=1 && tipo!=2){
                        System.out.println("Type " + current +" is not available.");
                        current=sin.readLine();
                        while(isNumeric(current)==false){
                            current=sin.readLine();
                         }
                        tipo=Integer.parseInt(current);
                    }
                    int idaux;
                    if((idaux=send("addUser " + username + " " +  password + " " + tipo ,out,r))!=-1)
                        System.out.println("You successfully registered with ID "+idaux);
                    else System.out.println("That username is not available.");
                }
            }while (opcao!=0);
            out.println("kill");
            r.stop();
            cs.shutdownOutput();
            out.close();
            in.close();
            cs.close();
        }catch(Exception e){e.printStackTrace();}
    }

    public static int send(String op, PrintWriter out, ClientReader r ){
        int ret=-15;
        String aux;
        try{
            out.println(op);
            synchronized(r.answers){
                while(r.answers.isEmpty()){
                    r.answers.wait();
                }
            }
            ret= Integer.parseInt(r.answers.get(0));
            r.answers.remove(0);
        }catch(Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    public static void printMenu1(){
        System.out.println("1 - Login");
        System.out.println("2 - Register");
        System.out.println("0 - Quit");
        System.out.println("Option: ");
    }

    public static void printMenu2Buyer(){
        System.out.println("1 - Auctions list");
        System.out.println("2 - Place a bid");
        System.out.println("0 - Logout");
    }

    public static void printMenu2Seller(){
        System.out.println("1 - Auctions list");
        System.out.println("2 - Add auction");
        System.out.println("3 - End Auction");
        System.out.println("0 - Logout");
    }
    
    

    public static void printLeiloes(int userId , PrintWriter out, ClientReader r){
        ArrayList<String> ret= new ArrayList<>();
        int aux;
        String s;
        try{
            out.println("getLeiloes " + userId);
            synchronized(r.answers){
                while(r.answers.isEmpty()){
                    r.answers.wait();
                }
            }
            aux= Integer.parseInt(r.answers.get(0));
            r.answers.remove(0);
            synchronized(r.answers){
                while(r.answers.size()<aux){
                    r.answers.wait();
                }
            }
            for(int i =0 ; i<aux ; i++){
                while(r.answers.isEmpty()){}
                ret.add(r.answers.get(0));
                r.answers.remove(0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        for(String x : ret)
            System.out.println(x);
    }
}



class ClientReader extends Thread{
    BufferedReader in;
    ArrayList<String> answers;
    public ClientReader(BufferedReader in){
        this.in=in;
        answers=new ArrayList<>();
    }
    public void run(){
        String s;
        try {
            while(true){
                while((s=in.readLine())!=null){
                    if(s.charAt(0)==':') System.out.println(s);
                    else{
                        synchronized(answers){
                            answers.add(s);
                            answers.notify();
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}