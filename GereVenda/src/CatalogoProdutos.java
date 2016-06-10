
import java.util.HashSet;
import java.util.Objects;
import java.util.TreeSet;


/**
 *
 * @author munybt
 */
public class CatalogoProdutos implements java.io.Serializable {
    
    private HashSet<Produto> produtos;

    public CatalogoProdutos(){
        produtos= new HashSet<Produto>();
    }

    public HashSet<Produto> getProdutos() {
        return produtos;
    }
    
    public void setProdutos(HashSet<Produto> p){
        this.produtos=p;
    }
    
    public void addProduto(Produto produto){
        produtos.add(produto);
    }
    
    public int size(){
        return produtos.size();
    }
    
    public boolean existeProduto(Produto p){
        return produtos.contains(p);
    }

    @Override
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
        final CatalogoProdutos other = (CatalogoProdutos) obj;
        return true;
    }
    
    
}
