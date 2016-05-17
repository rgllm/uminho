
import java.util.*;

public class Vendedor extends Utilizador{

    private Set<Imovel> portfolio;
    private Set<Imovel> historico;

    /*           Construtores         */
    public Vendedor(){
        super();
        portfolio=new TreeSet<Imovel>();
        historico=new TreeSet<Imovel>();
    }

    public Vendedor(String email,String nome,String password,String morada,String data_nascimento){
        super(email,nome,password,morada,data_nascimento);
        this.portfolio=new TreeSet<>();
        this.historico=new TreeSet<>();
    }

    public Vendedor(Vendedor x){
        super(x.getEmail() , x.getNome() , x.getPassword() , x.getMorada() , x.getDataNasc() );
        this.portfolio=x.getPortfolio();
        this.historico=x.getHistorico();
    }

     public Vendedor(Set<Imovel>portfolio , Set<Imovel> historico){
        this();
        setPortfolio(portfolio);
        setHistorico(historico);
    }

    /*      Métodos de instância    */

    public Set<Imovel> getPortfolio(){
        Set<Imovel> res = new TreeSet<Imovel>();
        for(Imovel i: portfolio){
            res.add(i.clone());
        }

        return res;
    }

    public Set<Imovel> getHistorico(){
        Set<Imovel> res = new TreeSet<Imovel>();
        for(Imovel i: historico){
            res.add(i.clone());
        }

        return res;
    }

    public void setPortfolio(Set<Imovel> portfolio){
        this.portfolio.clear();
        for(Imovel i: portfolio){
            this.portfolio.add(i.clone());
        }
    }

    public void setHistorico(Set<Imovel> historico){
        this.historico.clear();
        for(Imovel i: historico){
            this.historico.add(i.clone());
        }
    }

    public void paraVenda(Imovel i){
        portfolio.add(i.clone());
    }

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Vendedor other = (Vendedor) obj;
        if (Objects.equals(this.portfolio, other.portfolio) &&
            Objects.equals(this.historico, other.historico)) {
            return true;
        }
        return false;
    }

    public Vendedor clone(){
          return new Vendedor(this);
    }
}

