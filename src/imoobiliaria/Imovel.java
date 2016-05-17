import java.util.Objects;
import java.lang.Object;

public class Imovel implements Comparable<Imovel>{
    private String id;
    private String rua;
    private Double preco;
    private Double precoMinimo;
    private Estado_Imovel estado;

    /*           Construtores         */
    public Imovel(){
        id=new String("");
        rua=new String("");
        preco=0.0;
        precoMinimo=0.0;
        estado=Estado_Imovel.Outro;
    }

    public Imovel(String rua,double preco,double precoMinimo,Estado_Imovel es){
        this.id=new String(id);
        this.rua=new String(rua);
        this.preco=preco;
        this.precoMinimo=precoMinimo;
        this.estado=es;
    }

    public Imovel(Imovel i){
        this.id=new String(i.getId());
        this.rua=new String(i.getRua());
        this.preco=i.getPreco();
        this.precoMinimo=i.getPrecoMinimo();
        this.estado=i.getEstado();
    }

    /*      Métodos de instância    */
    public String getId() {return new String(id);}
    public String getRua() {return new String(rua);}
    public Double getPreco() {return preco;}
    public Double getPrecoMinimo() {return precoMinimo;}
    public Estado_Imovel getEstado(){return estado;}

    public void setId(String id) {this.id = id;}
    public void setRua(String rua) {this.rua = rua;}
    public void setPreco(Double preco) {this.preco = preco;}
    public void setPrecoMinimo(Double precoMinimo) {this.precoMinimo = precoMinimo;}
    public void setEstado(Estado_Imovel estado) {this.estado=estado;}

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Imovel other = (Imovel) obj;
        if (Objects.equals(this.id, other.id) &&
            Objects.equals(this.rua, other.rua) &&
            Objects.equals(this.preco, other.preco) &&
            Objects.equals(this.precoMinimo, other.precoMinimo) &&
            Objects.equals(this.estado, other.estado)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Imovel{" + "id=" + id + ", rua=" + rua + ", preco=" + preco + ", precoMinimo=" + precoMinimo + ", Estado=" + estado + '}';
    }

    public Imovel clone(){
        return new Imovel(this);
    }

    public int compareTo(Imovel i){
        if(this.getPreco()<i.getPreco())
            return -1;
        if(this.getPreco()<i.getPreco())
            return 1;
        return 0;
    }

}