
/**
 *
 * @author munybt
 */
import java.util.Comparator;



public class ComparatorProduto implements Comparator<Produto>{    
    public int compare(Produto x, Produto y){
        return x.compareTo(y);
    }
}

