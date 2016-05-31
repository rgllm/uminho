
import java.util.TreeSet;


/**
 *
 * @author munybt
 */
public class CatalogoProdutos implements Serializable {
    
    private TreeSet<Produto> produtos;

    public CatalogoProdutos(){
        produtos= new TreeSet<Produto>();
    }

    public TreeSet<Produto> getProdutos() {
        return produtos;
    }
    
    public void addProduto(Produto produto){
        produtos.add(produto);
    }
}
