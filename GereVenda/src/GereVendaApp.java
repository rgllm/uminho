
import java.io.*;

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
            /*System.out.println("Clientes lidos: "+catalogoClientes.size());*/
            
            Crono.start();
            hip.setCatalogoProdutos(Leitura.leituraProdutos());
            Crono.stop();
            System.out.println("Tempo leitura Produtos: "+Crono.print());
            /*System.out.println("Produtos lidos: "+catalogoProdutos.size()); */
            
            Crono.start();
            hip.leituraVendas("Vendas_1M.txt");
            Crono.stop();
            System.out.println("Tempo leitura Vendas: "+Crono.print());
            
            /*
            Crono.start();
            hip.lerEstado("hipermercado.1m");
            Crono.stop();
            System.out.println("Tempo leitura estado: "+Crono.print());
            

            hip.gravarEstado("hipermercado.1m");
            */
            
        }
        catch(NullPointerException e){
            System.out.println("Ficheiro não encontrado.\n");
        }
        /*
        catch( IOException | ClassNotFoundException e){
            System.out.println("Erro na escrita/leitura do ficheiro.\n");
            e.printStackTrace();
        }
        */

    }
    
    
}
