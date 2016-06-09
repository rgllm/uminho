/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rgllm
 */
public class GereVendaApp {
    
    private static Hipermercado hip = new Hipermercado();
    
 
    public static void main(String [] args){
        
        try{
            Crono.start();
            hip.setCatalogoClientes(Leitura.leituraClientes());
            Crono.stop();
            System.out.println("Tempo leitura clientes: "+Crono.print());
            /*System.out.println("Clientes lidos: "+catalogoClientes.size()); */
            
            Crono.start();
            hip.setCatalogoProdutos(Leitura.leituraProdutos());
            Crono.stop();
            System.out.println("Tempo leitura Produtos: "+Crono.print());
            /*System.out.println("Produtos lidos: "+catalogoProdutos.size()); */
            
            Crono.start();
            hip.leituraVendas("Vendas_5M.txt");
            Crono.stop();
            System.out.println("Tempo leitura Faturação Global: "+Crono.print());
            //System.out.println("Compras da filial 1: "+nVendasPorFilial(3,vendas));
            //System.out.println("Compras preco 0: "+nVendasPrecoZero(vendas));
            //System.out.println("Produtos por letra(Compras): "+produtosLetra('A',vendas));
           /* System.out.println("Tempo leitura vendas: " + Crono.print());
            System.out.println("Linhas lidas: "+vendas.size()); */
            /*Crono.start();
            faturaGlobal(vendas);
            Crono.stop();
            System.out.println("Tempo faturacao global: "+Crono.print()); */
            
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
