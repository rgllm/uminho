/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.io.*;
import java.util.stream.Collectors;


public class GereVenda { 
    
    private static CatalogoProdutos catalogoProdutos;   
    private static CatalogoClientes catalogoClientes;
    private static Faturacao faturacaoGlobal;
    private Faturacao faturacaoFilial1;
    private Faturacao faturacaoFilial2;
    private Faturacao faturacaoFilial3;
    private Filial vendasFilial1;
    private Filial vendasFilial2;
    private Filial vendasFilial3;

    
    
    /*public static void faturaGlobal(ArrayList<Venda> vendas){
        faturacaoGlobal=new Faturacao();
        catalogoProdutos.forEach( p ->{
                faturacaoGlobal.setFaturacaoProduto(p,vendas.
                                                      stream().
                                                      filter(v -> v.getProduto().equals(p)).
                                                      collect(Collectors.toCollection(TreeSet::new)));
                }
        );
     
    }*/

    public static void main(String [] args){
        ArrayList<Venda> vendas=new ArrayList<>();

        try{Crono.start();
            catalogoClientes=Leitura.leituraClientes();
            Crono.stop();
            System.out.println("Tempo leitura clientes: "+Crono.print());
            System.out.println("Clientes lidos: "+catalogoClientes.size());
            
            Crono.start();
            catalogoProdutos=Leitura.leituraProdutos();
            Crono.stop();
            System.out.println("Tempo leitura Produtos: "+Crono.print());
            System.out.println("Produtos lidos: "+catalogoProdutos.size());
            
            Crono.start();
            vendas=Leitura.leituraVendas("Vendas_1M.txt");
            Crono.stop();
            //System.out.println("Compras da filial 1: "+nVendasPorFilial(3,vendas));
            //System.out.println("Compras preco 0: "+nVendasPrecoZero(vendas));
            //System.out.println("Produtos por letra(Compras): "+produtosLetra('A',vendas));
            System.out.println("Tempo leitura vendas: " + Crono.print());
            System.out.println("Linhas lidas: "+vendas.size());
            Crono.start();
            /*faturaGlobal(vendas);*/
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