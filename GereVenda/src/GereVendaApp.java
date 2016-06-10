
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

public class GereVendaApp {
    
    private static Hipermercado hip;
    private static Menu menuPrincipal;
    
    
    private static void carregarMenus() {
        String[] principal={"Ler os ficheiros com os dados","Gravar estado atual do programa","Query 1","Query 2","Query 3","Query 4"};
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
    
    private static void initApp() {
        try {
            System.out.println("A ler dados...");
            hip = Hipermercado.lerEstado("GereVenda.data");
            System.gc();
            
        }
        catch (IOException e) {
            hip = new Hipermercado();
            System.out.println("Não foi possível carregar os dados!\nO ficheiro GereVenda.data não existe.\n");
        }
        catch (ClassNotFoundException e) {
           hip = new Hipermercado();
            System.out.println("Não consegui ler os dados!\nO ficheiro tem um formato desconhecido.");
        }
        catch (ClassCastException e) {
           hip = new Hipermercado();
            System.out.println("Não consegui ler os dados!\nErro de formato.");
        }
    }
    
    public static void gravar(){
        try{
           hip.gravarEstado("GereVenda.data");
           System.out.println("Dados gravados com sucesso.");
        }
        catch(IOException e){
            System.out.println("Não consegui gravar os dados.");
        }
    }
    
    public static void query1Menu(){
        HashSet<Produto> lista;
        Crono.start();
        lista=hip.query1();
        Crono.stop();
        System.out.println("Resultado obtido em " + Crono.print()+"s");
        System.out.println("Produtos não comprados:\nTotal: " + lista.size());
        lista.forEach(x -> System.out.println(x.getCodigo()));
    }
    
    public static void query2Menu(){
        ParFloat resultado;
        int mes;
        System.out.print("\nInsira o mês: ");
        mes=Input.lerInt();
        Crono.start();
        resultado=hip.query2(mes);
        Crono.stop();
        System.out.println("Tempo: "+ Crono.print()+"s");
        System.out.println("Total vendas realizadas no mês " + mes + " -> " + resultado.getSecond());
        System.out.println("Total de clientes distintos que compraram no mês " + mes + " -> " + resultado.getFirst());    
    }
    
    public static void query3Menu(){
        ArrayList<TripleFloat> resultado = new ArrayList<>();
        String codigoCliente;
        System.out.print("\nCliente: ");
        codigoCliente=Input.lerString();
        Crono.start();
        resultado=hip.query3(codigoCliente);
        Crono.stop();
        System.out.println("Tempo: "+ Crono.print()+"s");
        System.out.println("Mês -> Número compras -> Produtos Comprados -> Valor Total");
        for(int i=0;i<12;i++){
            System.out.println("Mês: "+ (i+1) + " -> " + 
                    (int)resultado.get(i).getFirst().getFirst() + " -> " + 
                    (int)resultado.get(i).getFirst().getSecond() + " -> " + 
                    resultado.get(i).getSecond());
        }
    }
    
     public static void query4Menu(){
        ArrayList<TripleFloat> resultado = new ArrayList<>();
        String codigoProduto;
        System.out.print("\nProduto: ");
        codigoProduto=Input.lerString();
        Crono.start();
        resultado=hip.query4(codigoProduto);
        Crono.stop();
        System.out.println("Tempo: "+ Crono.print()+"s");
        System.out.println("Mês -> Número Unidades -> Número Clientes -> Valor Total");
        for(int i=0;i<12;i++){
            System.out.println("Mês: "+ (i+1) + " -> " + 
                    (int)resultado.get(i).getFirst().getFirst() + " -> " + 
                    (int)resultado.get(i).getFirst().getSecond() + " -> " + 
                    resultado.get(i).getSecond());
        }
    }
 
    public static void main(String [] args){
        carregarMenus();
        initApp();
        do{
           menuPrincipal.executa();
           switch(menuPrincipal.getOpcao()){
               case 1:
                   clearScreen();
                   leituraFicheiros();
                   break;
               case 2:
                   clearScreen();
                   gravar();
                   break;
               case 3:
                   clearScreen();
                   query1Menu();
                   break;
              case 4:
                  clearScreen();
                  query2Menu();
                  break;
              case 5:
                  clearScreen();
                  query3Menu();
                  break;
              case 6:
                  clearScreen();
                  query4Menu();
                  break;
           }
        } while (menuPrincipal.getOpcao()!=0);
        System.out.println("BYE!");
   }

}