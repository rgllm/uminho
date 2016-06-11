
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

    public Hipermercado(CatalogoProdutos catalogoProdutos, CatalogoClientes catalogoClientes, Faturacoes faturacao, Filial vendasFilial1, Filial vendasFilial2, Filial vendasFilial3, ArrayList<Integer> comprasMes) {
        this.catalogoProdutos = catalogoProdutos;
        this.catalogoClientes = catalogoClientes;
        this.faturacao = faturacao;
        this.vendasFilial1 = vendasFilial1;
        this.vendasFilial2 = vendasFilial2;
        this.vendasFilial3 = vendasFilial3;
    }
    
    public Hipermercado(Hipermercado h){
        this.catalogoProdutos = h.getCatalogoProdutos();
        this.catalogoClientes = h.getCatalogoClientes();
        this.faturacao = h.getFaturacao();
        this.vendasFilial1 = h.getVendasFilial1();
        this.vendasFilial2 = h.getVendasFilial2();
        this.vendasFilial3 = h.getVendasFilial3();
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
    
    public ParFloat query2(int mes) throws MesNaoExisteException{
        HashSet<Cliente> clientesMes = new HashSet<>();
        int totalVendas=0;
        if(mes<0 || mes >12){
            throw new MesNaoExisteException("O mês está incorreto. O mês tem que estar entre 1 e 12.");
        }
        else{
            for(HashSet<Venda> vendas: faturacao.getFaturacaoGlobal().getFaturacao().values()){
               Set<Venda> aux = vendas.stream().filter(x->x.getMes()==mes).collect(Collectors.toSet()); 
               totalVendas+=aux.size();
               for(Venda x : aux)
                    clientesMes.add(x.getCliente());
           }
       
           return new ParFloat((float)clientesMes.size(),(float)totalVendas);
       }
    }
   
    public ArrayList<TripleFloat> query3(String codigoCliente) throws ClienteNaoExisteException{
        ArrayList<TripleFloat> res = new ArrayList<>();
        ArrayList<HashSet<Produto>> produtosMes = new ArrayList<>();
        if(catalogoClientes.existeCliente(new Cliente(codigoCliente))==false){
            throw new ClienteNaoExisteException("O cliente pedido não existe");
        }
        else{
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
    }
    
    public ArrayList<TripleFloat> query4(String codigoProduto) throws ProdutoNaoExisteException{
        ArrayList<TripleFloat> res = new ArrayList<>();
        ArrayList<HashSet<Cliente>> clientesMes = new ArrayList<>(); 
        for(int i=0;i<12;i++){
            res.add(new TripleFloat(new ParFloat(0,0),0));
            clientesMes.add(new HashSet<Cliente>());
        }
        if(catalogoProdutos.existeProduto(new Produto(codigoProduto))==false){
            throw new ProdutoNaoExisteException("O produto pedido não existe");
        }
        else{
            Produto arg = new Produto(codigoProduto);
                for (Venda y : faturacao.getFaturacaoGlobal().getFaturacao().get(arg)){                 
                        res.get(y.getMes()-1).getFirst().addFirst((float)y.getUnidades());
                        res.get(y.getMes()-1).addSecond((float)(y.getUnidades()*y.getPreco()));
                        clientesMes.get(y.getMes()-1).add(y.getCliente());
                    
                }
            for(int i=0;i<12;i++)
            res.get(i).getFirst().addSecond((float)(clientesMes.get(i).size()));
            return res;
        }
    } 
    
    public TreeSet<ParProdutoInt> query5(String codigoCliente) throws ClienteNaoExisteException{
        TreeMap<Produto,Integer> res = new TreeMap<>();
        TreeSet<ParProdutoInt> ret= new TreeSet<>();
         if(catalogoClientes.existeCliente(new Cliente(codigoCliente))==false){
            throw new ClienteNaoExisteException("O cliente pedido não existe");
        }
         else{
            for(HashSet<Venda> lVendas : faturacao.getFaturacaoGlobal().getFaturacao().values() )
                for(Venda v : lVendas)
                    if(v.getCliente().getCodigo().equals(codigoCliente))
                        if(res.containsKey(v.getProduto()))
                            res.replace(v.getProduto(),res.get(v.getProduto())+v.getUnidades());
                        else res.put(v.getProduto(), v.getUnidades());


            res.forEach((k,v)-> ret.add(new ParProdutoInt(k,v)));
            return ret;
         }
    }
   
    public TreeSet<ParProdutoInt> query6(int x){
        TreeSet<ParProdutoInt> ret= new TreeSet<>();
        TreeSet<ParProdutoInt> res= new TreeSet<>();
        Iterator<ParProdutoInt> it=null;
        int sum,i;
        for(HashSet<Venda> lVendas : faturacao.getFaturacaoGlobal().getFaturacao().values() ){
            sum=0;
            Produto aux=null;
            boolean flag=true;
            for(Venda v : lVendas){
                if(flag){aux=v.getProduto();flag=false;}
                sum+=v.getUnidades();
            ret.add(new ParProdutoInt(aux,sum));
            }
        }
        it= ret.iterator();
        i=0;
        while(i<x){
            res.add(it.next());
            i++;
        }
        for(ParProdutoInt par : res){
            HashSet<Cliente> clientes = new HashSet<>(); 
            for (Venda y : faturacao.getFaturacaoGlobal().getFaturacao().get(par.getProduto())){                 
                clientes.add(y.getCliente());
            }
            par.setInteiro(clientes.size());
        }
        return res;
    }
    public ArrayList<Cliente> query7(){
        ArrayList<Cliente> ret=new ArrayList<>(9);
        TreeMap<Cliente,Float> res = new TreeMap<>();
        TreeSet<ParClienteFloat> aux = new TreeSet<>();
        Iterator<ParClienteFloat> it= null;
        Filial f;
        for(int i=0;i<3;i++){
            if(i==0)f=vendasFilial1;
            else if(i==1)f=vendasFilial2;
            else f=vendasFilial3;
            for(Venda v : f.getVendas()){
                if(res.containsKey(v.getCliente()))
                    res.replace(v.getCliente(), (float)(res.get(v.getCliente()) + (v.getUnidades()*v.getPreco())));
                else res.put(v.getCliente(),(float)(v.getUnidades()*v.getPreco()));
            }
            res.forEach( (k,v) -> aux.add(new ParClienteFloat(k,v)));
            it= aux.iterator();
            for(int j = i*3; j<i*3+3;j++){
                ret.add(j,it.next().getCliente());
            }
        }
        return ret;
    }
    
    public ArrayList<ParClienteFloat> query8(int x){
        TreeMap<Cliente,Integer> ret= new TreeMap<>();
        TreeSet<ParClienteFloat> res= new TreeSet<>();
        ArrayList<ParClienteFloat> resultado= new ArrayList<>();
        Iterator<ParClienteFloat> it=null;
        int sum,i;
        for(HashSet<Venda> lVendas : faturacao.getFaturacaoGlobal().getFaturacao().values() ){
            HashSet<Cliente> aux= new HashSet<>();
            Cliente aux1=null;
            for(Venda v : lVendas)
                aux.add(v.getCliente());
                
            aux.forEach(cliente-> {
                    if (ret.containsKey(cliente))
                        ret.replace(cliente,ret.get(cliente)+1);
                    else ret.put(cliente,1);
            });
        }
        ret.forEach((k,v)->
                res.add(new ParClienteFloat(k,(float)v)));
       
        it= res.iterator();
        i=0;
        while(i<x){
            resultado.add(it.next());
            i++;
        }
        return resultado;
    
    }

    public ArrayList<ParClienteFloat> query9(String codigoProduto, int x) throws ProdutoNaoExisteException{
        TreeMap<Cliente,ParFloat> res = new TreeMap<>();
        TreeSet<ParClienteFloat> ret= new TreeSet<>();
        Iterator<ParClienteFloat> it=null;
        ArrayList<ParClienteFloat> resultado= new ArrayList<>();
        if(catalogoProdutos.existeProduto(new Produto(codigoProduto))==false){
            throw new ProdutoNaoExisteException("O produto pedido não existe");
        }
        else{
            for(Venda v : faturacao.getFaturacaoGlobal().getFaturacaoProduto(new Produto(codigoProduto)))
                if(res.containsKey(v.getCliente())){
                    res.get(v.getCliente()).addFirst((float)v.getUnidades());
                    res.get(v.getCliente()).addSecond((float)(v.getUnidades()*v.getPreco()));
                }
                else res.put(v.getCliente(),new ParFloat((float)v.getUnidades(),(float)(v.getUnidades()*v.getPreco())));
            res.entrySet().forEach(entry -> ret.add(new ParClienteFloat(entry.getKey(),entry.getValue().getSecond())));

            it=ret.iterator();
            int i=0;
            while(i<x && it.hasNext()){
                Cliente aux= it.next().getCliente();
                resultado.add(new ParClienteFloat(aux,res.get(aux).getSecond()));
                i++;
            }
            return resultado;

        }
       
    }
}
