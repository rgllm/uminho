import java.util.*;

public class Faturacao {
    
    private HashMap<Produto,HashSet<Venda>> faturacao;

    public Faturacao(){
        faturacao=new HashMap<Produto,HashSet<Venda>>(200000,1);
    }
    
    public void setFaturacao(HashMap<Produto,HashSet<Venda>> f){
        this.faturacao=f;
    }
    
    public HashMap<Produto,HashSet<Venda>> getFaturacao(){
        return this.faturacao;
    }
    
    public HashSet<Venda> getFaturacaoProduto(Produto p){
        return faturacao.get(p);
    }
    
    public void setFaturacaoProduto(Produto p,HashSet<Venda> vendas){
        faturacao.put(new Produto(p),vendas);
    }
    
    public void addVenda(Venda v){
        if(faturacao.containsKey(v.getProduto())){
            faturacao.get(v.getProduto()).add(v);
        }
        else{
            HashSet<Venda> nova = new HashSet<>();
            nova.add(v);
            faturacao.put(v.getProduto(),nova);
        }
    }
    
    public Faturacao clone(){
        Faturacao ret = new Faturacao();
        HashMap<Produto, HashSet<Venda>> faturacao = new HashMap<>();
        
        this.faturacao.entrySet().forEach(x -> {
             HashSet<Venda> vendas = new HashSet<>();
             for(Venda y : x.getValue()){
                 vendas.add(y.clone());
             }
             faturacao.put(x.getKey().clone() ,vendas);
        }
             );
        ret.setFaturacao(faturacao);
        return ret;
    }    

    @Override
    public String toString() {
        return "Faturacao{" + "faturacao=" + faturacao + '}';
    }
    
}


/*
     public void insereVendaFG(Venda venda){
        
        TreeSet<Venda> cVendas = faturacao.get(venda.getProduto());
        cVendas.add(venda);
        faturacao.put(venda.getProduto(),cVendas);
    }
     
*/