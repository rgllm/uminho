/**
 *
 * @author rgllm
 */


import java.lang.*;
import java.util.Objects;

public class Venda implements Serializable {

    private final String produto;
    private final double preco;
    private final int unidades;
    private final char modo;
    private final String cliente;
    private final int mes;
    private final int filial;

    
    public Venda(String po, double pr, int u, char m, String c, int me, int f){
        produto=po;
        preco=pr;
        unidades=u;
        modo=m;
        cliente=c;
        mes=me;
        filial=f;
    }
    
    public Venda(Venda v){
        produto=v.getProduto();
        preco=v.getPreco();
        unidades=v.getUnidades();
        modo=v.getModo();
        cliente=v.getCliente();
        mes=v.getMes();
        filial=v.getFilial();
    }

    
    public String getProduto(){return produto;}
    public double getPreco(){return preco;}
    public int getUnidades(){return unidades;}
    public char getModo(){return modo;}
    public String getCliente(){return cliente;}
    public int getMes(){return mes;}
    public int getFilial(){return filial;}


    public Venda clone(){
       return new  Venda(this);
    }

    public String toString(){
        StringBuilder venda = new StringBuilder();
        
        venda.append("Produto ");
        venda.append(produto);
        venda.append(" Preco ");
        venda.append(preco);
        venda.append(" Unidades ");
        venda.append(unidades);
        venda.append(" Modo ");
        venda.append(modo);
        venda.append(" Cliente ");
        venda.append(cliente);
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
        if (!Objects.equals(this.cliente, other.cliente)) {
            return false;
        }
        return true;
    }
    
  

}
