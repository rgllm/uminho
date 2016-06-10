
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;


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
    
    public static Hipermercado lerEstado(String fich) throws IOException, ClassNotFoundException{
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
            System.out.println("Erro ao ler o ficheiro.\n");
        }
    }
    
    /*Querys*/
    
    //Falta a ordenação e a impressão em lista
    public HashSet<Produto> query1(){
        HashSet<Produto> lista = new HashSet<>(catalogoProdutos.getProdutos());
        faturacao.getFaturacaoGlobal().getFaturacao().keySet().forEach(p->lista.remove(p));
        return lista;       
    }
    
    public ParFloat query2(int mes){
        HashSet<Cliente> clientesMes = new HashSet<>();
        int totalVendas=0;
        
        for(HashSet<Venda> vendas: faturacao.getFaturacaoGlobal().getFaturacao().values()){
           Set<Venda> aux = vendas.stream().filter(x->x.getMes()==mes).collect(Collectors.toSet()); 
           totalVendas+=aux.size();
           for(Venda x : aux)
                clientesMes.add(x.getCliente());
       }
       
      return new ParFloat((float)clientesMes.size(),(float)totalVendas);
    }
   
    public ArrayList<TripleFloat> query3(String codigoCliente){
        ArrayList<TripleFloat> res = new ArrayList<>();
        ArrayList<HashSet<Produto>> produtosMes = new ArrayList<>(); 
        for(int i=0;i<12;i++){
            res.add(new TripleFloat(new ParFloat(0,0),0));
            produtosMes.add(new HashSet<Produto>());
        }
        try{
        for(HashSet<Venda> vendasProd : faturacao.getFaturacaoGlobal().getFaturacao().values()){
                for (Venda y : vendasProd)
                    if(y.getCliente().getCodigo().equals(codigoCliente)){
                        res.get(y.getMes()-1).getFirst().incFirst();
                        res.get(y.getMes()-1).addSecond((float)(y.getUnidades()*y.getPreco()));
                        produtosMes.get(y.getMes()-1).add(y.getProduto());
                    }
        }
        for(int i=0;i<12;i++)
            res.get(i).getFirst().addSecond((float)(produtosMes.get(i).size()));
        
        }
        catch(NullPointerException e){
            e.getStackTrace();
        }
            
     return res;
    }
    
    public ArrayList<TripleFloat> query4(String codigoProduto){
        ArrayList<TripleFloat> res = new ArrayList<>();
        ArrayList<HashSet<Cliente>> clientesMes = new ArrayList<>(); 
        for(int i=0;i<12;i++){
            res.add(new TripleFloat(new ParFloat(0,0),0));
            clientesMes.add(new HashSet<Cliente>());
        }
        try{
            Produto arg = new Produto(codigoProduto);
                for (Venda y : faturacao.getFaturacaoGlobal().getFaturacao().get(arg)){                 
                        res.get(y.getMes()-1).getFirst().addFirst((float)y.getUnidades());
                        res.get(y.getMes()-1).addSecond((float)(y.getUnidades()*y.getPreco()));
                        clientesMes.get(y.getMes()-1).add(y.getCliente());
                    
                }
        for(int i=0;i<12;i++)
            res.get(i).getFirst().addSecond((float)(clientesMes.get(i).size()));
        
        }
        catch(NullPointerException e){
            e.getStackTrace();
        }
            
     return res;
    } 
    
    public void query5(String codigoCliente){
        TreeMap<Produto,Integer> res = new TreeMap<>();
        
        for(HashSet<Venda> lVendas : faturacao.getFaturacaoGlobal().getFaturacao().values() )
            for(Venda v : lVendas)
                if(v.getCliente().getCodigo().equals(codigoCliente))
                    if(res.containsKey(v.getProduto()))
                        res.replace(v.getProduto(),res.get(v.getProduto())+v.getUnidades());
                    else res.put(v.getProduto(), v.getUnidades());
        
        System.out.println(res.toString());
    }
   
    
}
