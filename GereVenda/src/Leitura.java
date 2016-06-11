import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Leitura {
    
     public static ArrayList<String> readLinesWithBuff(String fich) {
        ArrayList<String> linhas = new ArrayList<>();
        BufferedReader inStream = null;
        String linha = null;
        try {
            inStream = new BufferedReader(new FileReader(fich));
        while( (linha = inStream.readLine()) != null )
            linhas.add(linha);
        }
        catch(IOException e)
        { System.out.println(e.getMessage()); return null; };
            return linhas;
    }
    
    /* Leitura Vendas */
    
    public static Venda parseLinhaVenda(String linha) {
    
        Venda lvenda;

        String[] campos = linha.split(" ");
        try{
           lvenda=new Venda(campos[0],                     // produto
                            Double.parseDouble(campos[1]), // preco
                            Integer.parseInt(campos[2]),   // unidades
                            campos[3].charAt(0),           // modo
                            campos[4],                     // cliente
                            Integer.parseInt(campos[5]),   // mes
                            Integer.parseInt(campos[6]));  // filial
        }
        catch(NumberFormatException | NullPointerException exc){
            return null;
        }

        return lvenda;
    }
    
    
    /* Leitura Clientes */
    
    public static CatalogoClientes parseAllClientes(ArrayList<String> linhas) {
       Cliente lcliente;
       CatalogoClientes catalogoClientes=new CatalogoClientes();
       
        for(String s : linhas){
            lcliente = new Cliente(s);
            catalogoClientes.addCliente(lcliente);
        }
        
        return catalogoClientes;

    }
    
    public static CatalogoClientes leituraClientes(String ficheiroClientes){
        CatalogoClientes catalogoClientes=new CatalogoClientes();

        try{
            catalogoClientes=parseAllClientes(readLinesWithBuff(ficheiroClientes));
            return catalogoClientes;
         
        }
        catch(NullPointerException e){
            System.out.println("You have to have a file.\n");
            return null;
        }
        
    }
    
    /* Leitura dos Produtos */
    
     public static CatalogoProdutos parseAllProdutos(ArrayList<String> linhas) {
       Produto lproduto;
       CatalogoProdutos catalogoProdutos=new CatalogoProdutos();
       
        for(String s : linhas){
            lproduto = new Produto(s);
            catalogoProdutos.addProduto(lproduto);
        }
        
        return catalogoProdutos;
    }
        
           
    public static CatalogoProdutos leituraProdutos(String ficheiroProdutos){
        CatalogoProdutos catalogoProdutos=new CatalogoProdutos();
      
        try{
            catalogoProdutos=parseAllProdutos(readLinesWithBuff(ficheiroProdutos));
            return catalogoProdutos;
         
        }
        catch(NullPointerException e){
            System.out.println("You have to have a file.\n");
            return null;
        }
        
    }
    
}
