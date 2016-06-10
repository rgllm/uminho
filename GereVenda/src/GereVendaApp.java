
import java.io.*;

public class GereVendaApp {
    
    private static Hipermercado hip = new Hipermercado();
    private static Menu menuPrincipal;
    
    
    private static void carregarMenus() {
        String[] principal={"Ler os ficheiros com os dados"};
        menuPrincipal = new Menu(principal);
    }
    
   public final static void clearScreen(){
       
    }

    private static void leituraFicheiros(){
        String ficheiroVendas;
        try{
            clearScreen();
            System.out.print("\nInsira o nome do ficheiro de vendas: ");
            ficheiroVendas=Input.lerString();
            Crono.start();
            hip.setCatalogoClientes(Leitura.leituraClientes());
            Crono.stop();
            System.out.println("Tempo leitura clientes: "+Crono.print());
            Crono.start();
            hip.setCatalogoProdutos(Leitura.leituraProdutos());
            Crono.stop();
            System.out.println("Tempo leitura Produtos: "+Crono.print());
            Crono.start();
            hip.leituraVendas(ficheiroVendas);
            Crono.stop();
            System.out.println("Tempo leitura Vendas: "+Crono.print());
        }
       catch(NullPointerException e){
            System.out.println("Um dos ficheiros não foi encontrado.\n");
        } 
    }
    
    public final static void printLogo(){
    }
 
    public static void main(String [] args) throws IOException{
        carregarMenus();
        //initApp();
        do{
           menuPrincipal.executa();
           switch(menuPrincipal.getOpcao()){
               case 1:
                   clearScreen();
                   leituraFicheiros();    
           }
        } while (menuPrincipal.getOpcao()!=0);
        /*try{
           hip.gravarEstado("GereVenda.data");
        }
        catch(IOException e){
            System.out.println("Não consegui gravar os dados.");
        } */
        System.out.println("Até à próxima!");
   }

}  
    
    
    
    
    
    
    
    
    
    
    
    /*
   }
        
        try{
            Crono.start();
            hip.setCatalogoClientes(Leitura.leituraClientes());
            Crono.stop();
            System.out.println("Tempo leitura clientes: "+Crono.print());
            /*System.out.println("Clientes lidos: "+catalogoClientes.size());*/
            
           /* Crono.start();
            hip.setCatalogoProdutos(Leitura.leituraProdutos());
            Crono.stop();
            System.out.println("Tempo leitura Produtos: "+Crono.print()); */
            /*System.out.println("Produtos lidos: "+catalogoProdutos.size()); */
            
            /*Crono.start();
            hip.leituraVendas("Vendas_1M.txt");
            Crono.stop();
            System.out.println("Tempo leitura Vendas: "+Crono.print()); */
            
            /*
            Crono.start();
            hip.lerEstado("hipermercado.1m");
            Crono.stop();
            System.out.println("Tempo leitura estado: "+Crono.print());
            

            hip.gravarEstado("hipermercado.1m");
            */
            
        /*}
        catch(NullPointerException e){
            System.out.println("Ficheiro não encontrado.\n");
        } */
        /*
        catch( IOException | ClassNotFoundException e){
            System.out.println("Erro na escrita/leitura do ficheiro.\n");
            e.printStackTrace();
        }
        */
