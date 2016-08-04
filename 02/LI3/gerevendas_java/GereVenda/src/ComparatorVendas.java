import java.util.Comparator;

public class ComparatorVendas implements Comparator<Venda>{    
    public int compare(Venda x, Venda y){
        return x.compareTo(y);
    }
}

