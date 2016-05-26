/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.io.*;
import java.util.stream.Collectors;


public class GereVenda { 
    
    private static TreeSet<Produto> catalogoProdutos;   
    private static TreeSet<Cliente> catalogoClientes;
    private static Faturacao faturacaoGlobal;
    private Faturacao faturacaoFilial1;
    private Faturacao faturacaoFilial2;
    private Faturacao faturacaoFilial3;
    private Filial vendasFilial1;
    private Filial vendasFilial2;
    private Filial vendasFilial3;

    
    public static ArrayList<String> readLinesWithBuff(String fich) {
        ArrayList<String> linhas = new ArrayList<>();
        BufferedReader inStream = null;
        String linha = null;
        
        try {
            inStream = new BufferedReader(new FileReader(fich));
            while( (linha = inStream.readLine()) != null ){
                linhas.add(linha);
            }
        }
        catch(IOException e)
        { System.out.println(e.getMessage()); return null; };
        
        return linhas;
    }
        
    public static void parseAllProdutos(ArrayList<String> linhas) {
       Produto lproduto;
       
        for(String s : linhas){
            lproduto = new Produto(s);
            catalogoProdutos.add(lproduto);
        }

    }
    
    public static void parseAllClientes(ArrayList<String> linhas) {
       Cliente lcliente;
       
        for(String s : linhas){
            lcliente = new Cliente(s);
            catalogoClientes.add(lcliente);
        }

    }
    
   
    public static void lerProdutos(){
      
        try{
            catalogoProdutos=new TreeSet<Produto>();
            parseAllProdutos(readLinesWithBuff("Produtos.txt"));
         
        }
        catch(NullPointerException e){
            System.out.println("You have to have a file.\n");
        }
        
    }
    
    public static void lerClientes(){
      
        try{
            catalogoClientes=new TreeSet<Cliente>();
            parseAllClientes(readLinesWithBuff("Clientes.txt"));
         
        }
        catch(NullPointerException e){
            System.out.println("You have to have a file.\n");
        }
        
    }
    
    public static void faturaGlobal(ArrayList<Venda> vendas){
        faturacaoGlobal=new Faturacao();
        catalogoProdutos.forEach( p ->{
                System.out.println(p.getCodigo());
                faturacaoGlobal.setFaturacaoProduto(p,vendas.
                                                      stream().
                                                      filter(v -> v.getProduto().equals(p)).
                                                      collect(Collectors.toCollection(TreeSet::new)));
                }
        );
     
    }

    public static void main(String [] args){
        ArrayList<Venda> vendas=new ArrayList<>();

        try{Crono.start();
            lerClientes();
            Crono.stop();
            System.out.println("Tempo leitura clientes: "+Crono.print());
            System.out.println("Clientes lidos: "+catalogoClientes.size());
            
            Crono.start();
            lerProdutos();
            Crono.stop();
            System.out.println("Tempo leitura Produtos: "+Crono.print());
            System.out.println("Produtos lidos: "+catalogoProdutos.size());
            
            Crono.start();
            vendas=LeituraVendas.leituraVendas("Vendas_1M.txt");
            Crono.stop();
            //System.out.println("Compras da filial 1: "+nVendasPorFilial(3,vendas));
            //System.out.println("Compras preco 0: "+nVendasPrecoZero(vendas));
            //System.out.println("Produtos por letra(Compras): "+produtosLetra('A',vendas));
            System.out.println("Tempo leitura vendas: " + Crono.print());
            System.out.println("Linhas lidas: "+vendas.size());
            Crono.start();
            faturaGlobal(vendas);
            Crono.stop();
            System.out.println("Tempo faturacao global: "+Crono.print());
            
            /*
            for(Produto p : catalogoProdutos){
                vendas.stream().filter(v -> v.getProduto().equals(p))
            }
            */
            
            
        }
        catch(NullPointerException e){
            System.out.println("You have to have a file.\n");
        }

    }

} 




/*

 public static int nVendasPorFilial(int filial,ArrayList<Venda> vendas){
        int count=0;
        for(Venda v : vendas){
            if(v.getFilial()==filial)
                count++;
        }
        return count;
    }

    public static int nVendasPrecoZero(ArrayList<Venda> vendas){
        int count=0;
        for(Venda v : vendas){
            if(v.getPreco()==0.0)
                count++;
        }
        return count;
    }

    public static int produtosLetra(char letra, ArrayList<Venda> vendas){
       int count=0;
       for(Venda v: vendas){
           if(v.getProduto().charAt(0)==letra){
               count++;
           }
       }
        return count;
    }
    */