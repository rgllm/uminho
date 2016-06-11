import java.util.Comparator;
    
public class ComparatorParProdInt implements Comparator<ParProdutoInt>{    
    public int compare(ParProdutoInt x, ParProdutoInt y){
        return x.compareTo(y);
    }
}
