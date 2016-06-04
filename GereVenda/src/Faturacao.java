/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;

/**
 *
 * @author rgllm
 */
public class Faturacao {
    
    private TreeMap<Produto,TreeSet<Venda>> faturacao;

    public Faturacao(){
        faturacao=new TreeMap<Produto,TreeSet<Venda>>();
    }

    /*public Faturacao(Faturacao faturacaoGlobal) {
        faturacao=new Fatu
    }*/
    
    public TreeSet<Venda> getFaturacaoProduto(Produto p){
        return faturacao.get(p);
    }
    
    public void setFaturacaoProduto(Produto p,TreeSet<Venda> vendas){
        faturacao.put(new Produto(p),vendas);
    }
    
    public TreeSet<Venda> get(Produto key){
        return faturacao.get(key);
    }
            
    public void put(Produto key, TreeSet<Venda> value){
        faturacao.put(key,value);
    }
    
     public void insereVendaFG(Venda venda){
        TreeSet<Venda> cVendas = new TreeSet<> (faturacao.get(venda.getProduto()));
        faturacao.put(venda.getProduto(),cVendas);
    }
}
