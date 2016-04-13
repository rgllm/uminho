
import java.util.Objects;
import java.lang.Object;

public class Imovel{
    private String id;
    private String rua;
    private Double preco;
    private Double precoMinimo;

    public Imovel(){
        id="";
        rua="";
        preco=0.0;
        precoMinimo=0.0;
    }

    public Imovel(String id,String rua,double preco,double precoMinimo){
        this.id=new String(id);
        this.rua=new String(rua);
        this.preco=preco;
        this.precoMinimo=precoMinimo;
    }

    public Imovel(Imovel i){
        this.id=i.getId();
        this.rua=i.getRua();
        this.preco=i.getPreco();
        this.precoMinimo=i.getPrecoMinimo();
    }

    public String getId() {return new String(id);}
    public String getRua() {return new String(rua);}
    public Double getPreco() {return preco;}
    public Double getPrecoMinimo() {return precoMinimo;}

    public void setId(String id) {this.id = id;}
    public void setRua(String rua) {this.rua = rua;}
    public void setPreco(Double preco) {this.preco = preco;}
    public void setPrecoMinimo(Double precoMinimo) {this.precoMinimo = precoMinimo;}

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Imovel other = (Imovel) obj;
        if (Objects.equals(this.id, other.id) && Objects.equals(this.rua, other.rua)
            && Objects.equals(this.preco, other.preco) && Objects.equals(this.precoMinimo, other.precoMinimo)) {
            return true;
        }
        return false;
    }

   public String toString() {
        return "Imovel{" + "id=" + id + ", rua=" + rua + ", preco=" + preco + ", precoMinimo=" + precoMinimo + '}';
    }



}