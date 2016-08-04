import java.util.Comparator;

public class ComparatorImovel implements Comparator<Imovel>{    
    public int compare(Imovel i1,Imovel i2){
        return i1.compareTo(i2);
    }
}