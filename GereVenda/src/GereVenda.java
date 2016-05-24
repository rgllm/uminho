/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.io.*;


public class GereVenda { 
    
    private static TreeSet<Produto> catalogoProdutos;
    private TreeSet<Cliente> catalogoClientes;
    private Faturacao faturacaoGlobal;
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
        while( (linha = inStream.readLine()) != null )
            linhas.add(linha);
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
    
   
    public static void lerProdutos(){
      
        try{
            parseAllProdutos(readLinesWithBuff("Produtos.txt"));
         
        }
        catch(NullPointerException e){
            System.out.println("You have to have a file.\n");
        }
        
    }

    public static void main(String [] args){
        ArrayList<Venda> vendas=new ArrayList<>();

        try{
            //catalogoProdutos= lerProdutos();
            //CatalogoClientes=lerClientes();
            Crono.start();
            vendas=LeituraVendas.leituraVendas("Vendas_1M.txt");
            Crono.stop();
            //System.out.println("Compras da filial 1: "+nVendasPorFilial(3,vendas));
            //System.out.println("Compras preco 0: "+nVendasPrecoZero(vendas));
            //System.out.println("Produtos por letra(Compras): "+produtosLetra('A',vendas));
            System.out.println("Tempo leitura vendas: " + Crono.print());
            System.out.println("Linhas lidas: "+vendas.size());

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