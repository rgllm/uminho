
import java.util.Objects;

public class Produto implements Serializable,Comparable<Produto> {
    private String codigo;

    public Produto(String codigo) {
        this.codigo = codigo;
    }
    
    public Produto(Produto p) {
        this.codigo = p.getCodigo();
    }

    public String getCodigo() {return codigo;}
    public void setCodigo(String codigo) {this.codigo = codigo;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final Produto other = (Produto) obj;
        if (!this.codigo.equals(other.codigo)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Produto{" + "codigo=" + codigo + '}';
    }
    
    public int compareTo(Produto p){
        return this.getCodigo().compareTo(p.getCodigo());
    }
}
    

