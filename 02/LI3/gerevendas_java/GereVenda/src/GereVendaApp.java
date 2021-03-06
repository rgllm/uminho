
import java.io.*;
import java.util.*;

public class GereVendaApp implements Serializable {
    
    private static Hipermercado hip;
    private static Menu menuPrincipal;
    private static String ficheiroVendas;
    private static TripleFloat resultadoLeitura;
    
    
    
    private static void carregarMenus() {
        String[] principal={"Lista com os códigos dos produtos nunca comprados e respectivo total.",
                            "Determinar o número total global de vendas realizadas e o número total de clientes distintos que as fizeram.",
                            "Determinar para um cliente, para cada mês, quantas compras fez, quantos produtos distintos comprou e quanto gastou no total.",
                            "Determinar para um produto, mês a mês, quantas vezes foi comprado, por quantos clientes diferentes e o total facturado.",
                            "Determinar para um cliente a lista de códigos de produtos que mais comprou.",
                            "Determinar o conjunto dos X produtos mais vendidos em todo o ano indicando o número total de distintos clientes que o compraram.",
                            "Determinar, para cada filial, a lista dos três maiores compradores em termos de dinheiro facturado.",
                            "Determinar os códigos dos X clientes que compraram mais produtos diferentes.",
                            "Determinar para um produto o conjunto dos X clientes que mais o compraram e o valor gasto.",
                            "Dados do último ficheiro de Vendas",
                            "Consultas Estatísticas",
                            "Ler os ficheiros com os dados",
                            "Gravar estado atual do programa"};
        menuPrincipal = new Menu(principal);
    }
    
   public final static void clearScreen(){
        
    }

    private static void leituraFicheiros(){
        String ficheiroProdutos,ficheiroClientes;
        try{
            clearScreen();
            System.out.print("\nInsira o nome do ficheiro de Produtos: ");
            ficheiroProdutos=Input.lerString();
            System.out.print("\nInsira o nome do ficheiro de Clientes: ");
            ficheiroClientes=Input.lerString();
            System.out.print("\nInsira o nome do ficheiro de Vendas: ");
            ficheiroVendas=Input.lerString();
            Crono.start();
            hip.setCatalogoClientes(Leitura.leituraClientes(ficheiroClientes));
            Crono.stop();
            System.out.println("\nTempo leitura Clientes: "+Crono.print());
            Crono.start();
            hip.setCatalogoProdutos(Leitura.leituraProdutos(ficheiroProdutos));
            Crono.stop();
            System.out.println("Tempo leitura Produtos: "+Crono.print());
            Crono.start();
            resultadoLeitura=hip.leituraVendas(ficheiroVendas);
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
            hip = Hipermercado.lerEstado("hipermercado.dat");
            System.gc();
            
        }
        catch (IOException e) {
            hip = new Hipermercado();
            System.out.println("Não foi possível carregar os dados!\nO ficheiro hipermercado.dat não existe.\n");
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
           hip.gravarEstado("hipermercado.dat");
           System.out.println("Dados gravados com sucesso.");
        }
        catch(IOException e){
            System.out.println("Não consegui gravar os dados.");
        }
    }
    
    public static void query1Menu(){
        TreeSet<Produto> lista;
        Crono.start();
        lista=hip.query1();
        Crono.stop();
        System.out.println("Produtos não comprados: ");
        lista.forEach(x -> System.out.println(x.getCodigo()));
        System.out.println("Total : "+lista.size());
        System.out.println("Resultado obtido em " + Crono.print()+"s");

    }
    
    public static void query2Menu(){
        ParFloat resultado;
        int mes;
        System.out.print("\nInsira o mês: ");
        mes=Input.lerInt();
        try{
            Crono.start();
            resultado=hip.query2(mes);
            Crono.stop();
            System.out.println("Resultado obtido em " + Crono.print()+"s");            System.out.println("Total vendas realizadas no mês " + mes + " -> " + (int)resultado.getSecond());
            System.out.println("Total de clientes distintos que compraram no mês " + mes + " -> " + (int)resultado.getFirst());    
        }
        catch(MesNaoExisteException e){
            System.out.println(e.getMessage());
        }
    }
    
    public static void query3Menu(){
        ArrayList<TripleFloat> resultado;
        String codigoCliente;
        System.out.print("\nCliente: ");
        codigoCliente=Input.lerString();
        try{
            Crono.start();
            resultado=hip.query3(codigoCliente);
            Crono.stop();
            System.out.println("       Número compras | Produtos Comprados | Valor Total");
            for(int i=0;i<12;i++){
                System.out.println("Mês "+ (i+1) + ":       " + 
                        (int)resultado.get(i).getFirst().getFirst() + "        |           " + 
                        (int)resultado.get(i).getFirst().getSecond() + "        | " + 
                        resultado.get(i).getSecond());
            }
            System.out.println("Resultado obtido em " + Crono.print()+"s");

        }
        catch(ClienteNaoExisteException e){
            System.out.println(e.getMessage());
        }
    }
    
     public static void query4Menu(){
        ArrayList<TripleFloat> resultado;
        String codigoProduto;
        System.out.print("\nProduto: ");
        codigoProduto=Input.lerString();
        try{
            Crono.start();
            resultado=hip.query4(codigoProduto);
            Crono.stop();
            System.out.println("       Número Unidades | Número Clientes | Valor Total");
            for(int i=0;i<12;i++){
                System.out.println("Mês "+ (i+1) + " :      " + 
                        (int)resultado.get(i).getFirst().getFirst() + "                 " + 
                        (int)resultado.get(i).getFirst().getSecond() + "           " + 
                        resultado.get(i).getSecond());
            }
            System.out.println("Resultado obtido em " + Crono.print()+"s");

        }
        catch(ProdutoNaoExisteException e){
            System.out.println(e.getMessage());
        }
    }
     
     public static void query5Menu(){
        TreeSet<ParProdutoInt> resultado;
        String codigoCliente;
        System.out.print("\nCliente: ");
        codigoCliente=Input.lerString();
        try{
            Crono.start();
            resultado=hip.query5(codigoCliente);
            Crono.stop();
            System.out.println("Produto -> quantidade");
            resultado.forEach(x -> System.out.println(x.getProduto().getCodigo() + " -> " + x.getInteiro() ));
            System.out.println("Resultado obtido em " + Crono.print()+"s");
        }
        catch(ClienteNaoExisteException e){
            System.out.println(e.getMessage());
        }
     }
     
    
     public static void query6Menu(){
       ArrayList<ParProdutoInt> resultado;
       int n;
       System.out.print("Insira o numero de produtos: ");
       n=Input.lerInt();
       Crono.start();
       resultado=hip.query6(n);
       Crono.stop();
       System.out.println("Produto -> número de clientes");
       resultado.forEach(x -> System.out.println(x.getProduto().getCodigo() + " -> " + x.getInteiro() ));
       System.out.println("Resultado obtido em " + Crono.print()+"s");
    }
     
    public static void query7Menu(){
        ArrayList<Cliente> resultado;
        Crono.start();
        resultado=hip.query7();
        Crono.stop();
        System.out.println("Produto -> número de clientes");
        System.out.println("Filial 1 | Filial 2 | Filial 3");
        for(int i=0;i<3;i++)
            System.out.println(resultado.get(i).getCodigo()+"      "+
                               resultado.get(i+3).getCodigo()+"      "+
                               resultado.get(i+6).getCodigo());
        System.out.println("Resultado obtido em " + Crono.print()+"s");
    }
    
     public static void query8Menu(){
       ArrayList<ParClienteFloat> resultado;
       int n;
       System.out.print("Insira o número de clientes: ");
       n=Input.lerInt();
       Crono.start();
       resultado=hip.query8(n);
       Crono.stop();
       System.out.println("Cliente -> número de produtos");
       resultado.forEach(x -> System.out.println(x.getCliente().getCodigo() + " -> " + (int)x.getValor() ));
       System.out.println("Resultado obtido em " + Crono.print()+"s");
    }
     
     public static void query9Menu(){
       ArrayList<ParClienteFloat> resultado;
       int n;
       System.out.print("Insira o número de clientes: ");
       n=Input.lerInt();
       String codigoProduto;
       System.out.print("\nProduto: ");
       codigoProduto=Input.lerString();
       try{
       Crono.start();
       resultado=hip.query9(codigoProduto,n);
       Crono.stop();
       System.out.println("Cliente -> valor gasto");
       resultado.forEach(x -> System.out.println(x.getCliente().getCodigo() + " -> " + x.getValor() ));
       System.out.println("Resultado obtido em " + Crono.print()+"s");
       }
       catch(ProdutoNaoExisteException e){
           System.out.println(e.getMessage());
       }
    }
     
    public static void estatisticas1(){
        Crono.start();
        int num= hip.nClientesCompraram();
        System.out.println("Nome do Ficheiro de Vendas -> " + ficheiroVendas);
        System.out.println("Número de Vendas erradas -> " + ((int)resultadoLeitura.getFirst().getFirst() 
                                                             - resultadoLeitura.getFirst().getSecond())   );
        System.out.println("Número total de Produtos -> " + hip.getCatalogoProdutos().getProdutos().size());
        System.out.println("Número de diferentes produtos comprados ->" + hip.getFaturacao().getFaturacaoGlobal().getFaturacao().size());
        System.out.println("Número de Produtos não comprados -> " + (hip.getCatalogoProdutos().getProdutos().size() - 
                                                                    hip.getFaturacao().getFaturacaoGlobal().getFaturacao().size()));
        System.out.println("Número total de Clientes -> " + hip.getCatalogoClientes().getClientes().size());
        System.out.println("Número de Clientes que realizaram compras -> "+ num );
        System.out.println("Número de Clientes que não realizaram compras -> " + (hip.getCatalogoClientes().getClientes().size()
                                                                               - num ));
        System.out.println("Número de Vendas de valor 0 -> " + (int)resultadoLeitura.getSecond() );
        System.out.println("Faturação Total -> " + hip.totalFaturado());
        Crono.stop();
        System.out.println("Resultado obtido em " + Crono.print()+"s");
    }
    public static void estatisticas2(){
        Crono.start();
        ArrayList<ArrayList<Float>> res=hip.faturacaoMes();
        System.out.println("Numero total de compras");
        int i=0;
        for(int x : hip.totalCompras()){
            System.out.println("    Mês "+(i+1)+" -> "+x);
            i++;
        }
        
        System.out.println("\nFaturação total por mês");
        System.out.println("Mes     Global        Filial 1        Filial 2        Filial 3  ");
        for(i=0;i<12;i++){
            System.out.println((i+1) + "   " +
                                res.get(0).get(i) + "   " + 
                                res.get(1).get(i) + "    " + 
                                res.get(2).get(i) + "    " + 
                                res.get(3).get(i));
        }
        System.out.println("\nClientes por mês");
        i=0;
        for(int x : hip.clientesPorMes()){
            System.out.println("    Mês "+(i+1)+" -> "+x);
            i++;
        }
        Crono.stop();
        System.out.println("Resultado obtido em " + Crono.print()+"s");
        
    }
 
    public static void main(String [] args){
        carregarMenus();
        initApp();
        do{
           menuPrincipal.executa();
           switch(menuPrincipal.getOpcao()){
              case 1:
                   clearScreen();
                   query1Menu();
                   break;
              case 2:
                  clearScreen();
                  query2Menu();
                  break;
              case 3:
                  clearScreen();
                  query3Menu();
                  break;
              case 4:
                  clearScreen();
                  query4Menu();
                  break;
              case 5:
                  clearScreen();
                  query5Menu();
                  break;
              case 6:
                  clearScreen();
                  query6Menu();
                  break;
              case 7:
                  clearScreen();
                  query7Menu();
                  break;
              case 8:
                  clearScreen();
                  query8Menu();
                  break;
              case 9:
                  clearScreen();
                  query9Menu();
                  break;
              case 10:
                  clearScreen();
                  estatisticas1();
                  break;
              case 11:
                  clearScreen();
                  estatisticas2();
                  break;
              case 12:
                   clearScreen();
                   leituraFicheiros();
                   break;
               case 13:
                   clearScreen();
                   gravar();
                   break;
           }
        } while (menuPrincipal.getOpcao()!=0);
        System.out.println("BYE!");
   }

}