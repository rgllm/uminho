
import java.util.Comparator;
    
public class ComparatorClientes implements Comparator<Cliente>{    
    public int compare(Cliente x, Cliente y){
        return x.compareTo(y);
    }
}
