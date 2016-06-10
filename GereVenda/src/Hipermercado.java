/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.*;
import java.util.*;


public class Hipermercado implements java.io.Serializable { 
    
    private CatalogoProdutos catalogoProdutos;   
    private CatalogoClientes catalogoClientes;
    private Faturacoes faturacao;
    private Filial vendasFilial1;
    private Filial vendasFilial2;
    private Filial vendasFilial3;
    
    public Hipermercado(){
        this.catalogoProdutos = new CatalogoProdutos();
        this.catalogoClientes = new CatalogoClientes();
        this.faturacao = new Faturacoes();
        this.vendasFilial1 = new Filial();
        this.vendasFilial2 = new Filial();
        this.vendasFilial3 = new Filial();
    }

    public Hipermercado(CatalogoProdutos catalogoProdutos, CatalogoClientes catalogoClientes, Faturacoes faturacao, Filial vendasFilial1, Filial vendasFilial2, Filial vendasFilial3) {
        this.catalogoProdutos = catalogoProdutos;
        this.catalogoClientes = catalogoClientes;
        this.faturacao = faturacao;
        this.vendasFilial1 = vendasFilial1;
        this.vendasFilial2 = vendasFilial2;
        this.vendasFilial3 = vendasFilial3;
    }

    public CatalogoProdutos getCatalogoProdutos() {
        return catalogoProdutos;
    }

    public void setCatalogoProdutos(CatalogoProdutos catalogoProdutos) {
        this.catalogoProdutos = catalogoProdutos;
    }

    public CatalogoClientes getCatalogoClientes() {
        return catalogoClientes;
    }

    public void setCatalogoClientes(CatalogoClientes catalogoClientes) {
        this.catalogoClientes = catalogoClientes;
    }

    public Faturacoes getFaturacao() {
        return faturacao;
    }

    public void setFaturacao(Faturacoes faturacao) {
        this.faturacao = faturacao;
    }

    public Filial getVendasFilial1() {
        return vendasFilial1;
    }

    public void setVendasFilial1(Filial vendasFilial1) {
        this.vendasFilial1 = vendasFilial1;
    }

    public Filial getVendasFilial2() {
        return vendasFilial2;
    }

    public void setVendasFilial2(Filial vendasFilial2) {
        this.vendasFilial2 = vendasFilial2;
    }

    public Filial getVendasFilial3() {
        return vendasFilial3;
    }

    public void setVendasFilial3(Filial vendasFilial3) {
        this.vendasFilial3 = vendasFilial3;
    }
    
    
    /* Leitura das Vendas */
    
    public boolean verificaVenda(Venda venda) {        
        if(getCatalogoClientes().existeCliente(venda.getCliente()) &&
            getCatalogoProdutos().existeProduto(venda.getProduto()) &&
            venda.getPreco()>=0.0 && venda.getUnidades()>=0 )
            return true;
        return false;
    }
    
    public void gravarEstado(String fich) throws IOException{
         ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fich));
         out.writeObject(this);
         out.flush();
         out.close(); 
    }
    
    public Hipermercado lerEstado(String fich) throws IOException, ClassNotFoundException{
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(fich));
        Hipermercado ret=(Hipermercado) in.readObject();
        in.close();
        return ret;
    }
   
    public void leituraVendas(String fich){  
        ArrayList<String> linhasVendas = Leitura.readLinesWithBuff(fich);
        
       try{
           for(String s : linhasVendas){
            Venda venda = new Venda(Leitura.parseLinhaVenda(s));
            if(verificaVenda(venda)){
               faturacao.getFaturacaoGlobal().addVenda(venda);
               if(venda.getFilial()==1) {
                   faturacao.getFaturacaoFilial1().addVenda(venda);
                   vendasFilial1.getVendas().add(venda);
               }
               if(venda.getFilial()==2) {
                   faturacao.getFaturacaoFilial2().addVenda(venda);
                   vendasFilial2.getVendas().add(venda);
               }
               if(venda.getFilial()==3) {
                   faturacao.getFaturacaoFilial3().addVenda(venda);
                   vendasFilial3.getVendas().add(venda);
               }
            }
            }
        }
        catch(NullPointerException e){
            System.out.println("You have to have a file.\n");
        }
    }
}
