
import java.lang.*;
import java.util.Objects;

public class Venda implements Serializable ,Comparable<Venda>{

    private final Produto produto;
    private final double preco;
    private final int unidades;
    private final char modo;
    private final Cliente cliente;
    private final int mes;
    private final int filial;

    
    public Venda(String po, double pr, int u, char m, String c, int me, int f){
        produto=new Produto(po);
        preco=pr;
        unidades=u;
        modo=m;
        cliente=new Cliente(c);
        mes=me;
        filial=f;
    }
    
    public Venda(Venda v){
        produto=new Produto(v.getProduto());
        preco=v.getPreco();
        unidades=v.getUnidades();
        modo=v.getModo();
        cliente=new Cliente(v.getCliente());
        mes=v.getMes();
        filial=v.getFilial();
    }

    
    public Produto getProduto(){return produto;}
    public double getPreco(){return preco;}
    public int getUnidades(){return unidades;}
    public char getModo(){return modo;}
    public Cliente getCliente(){return cliente;}
    public int getMes(){return mes;}
    public int getFilial(){return filial;}

    public Venda clone(){
       return new Venda(this);
    }

    public String toString(){
        StringBuilder venda = new StringBuilder();
        
        venda.append("Produto ");
        venda.append(produto.getCodigo());
        venda.append(" Preco ");
        venda.append(preco);
        venda.append(" Unidades ");
        venda.append(unidades);
        venda.append(" Modo ");
        venda.append(modo);
        venda.append(" Cliente ");
        venda.append(cliente.getCodigo());
        venda.append(" Mes ");
        venda.append(mes);
        venda.append(" Filial ");
        venda.append(filial+"\n");
  
        return venda.toString();
    }

    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Venda other = (Venda) obj;
        if (Double.doubleToLongBits(this.preco) != Double.doubleToLongBits(other.preco)) {
            return false;
        }
        if (this.unidades != other.unidades) {
            return false;
        }
        if (this.modo != other.modo) {
            return false;
        }
        if(this.mes != other.mes){
            return false;
        }
        if (this.filial != other.filial) {
            return false;
        }
        if (!Objects.equals(this.produto, other.produto)) {
            return false;
        }
        if (!this.cliente.equals(other.cliente)) {
            return false;
        }
        return true;
    }
    
    public int compareTo(Venda v){
        if(produto.compareTo(v.getProduto())!=0) return produto.compareTo(v.getProduto());
        if(preco!=v.getPreco())
            if(preco<v.getPreco()) return -1;
            else return 1;
        if(unidades!=(v.getUnidades()))
            if(unidades<v.getUnidades()) return -1;
            else return 1;
        if(modo!=v.getModo())
            if(modo<v.getModo()) return -1;
            else return 1;
        if(cliente.compareTo(v.getCliente())!=0) return produto.compareTo(v.getProduto());
        if(mes!=v.getMes())
            if(mes<v.getMes()) return -1;
            else return 1;
        if(filial!=v.getFilial())
            if(filial<v.getFilial()) return -1;
            else return 1;
        return 0;
    }

    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.produto);
        hash = 83 * hash + (int) (Double.doubleToLongBits(this.preco) ^ (Double.doubleToLongBits(this.preco) >>> 32));
        hash = 83 * hash + this.unidades;
        hash = 83 * hash + this.modo;
        hash = 83 * hash + Objects.hashCode(this.cliente);
        hash = 83 * hash + this.mes;
        hash = 83 * hash + this.filial;
        return hash;
    }
    
}
