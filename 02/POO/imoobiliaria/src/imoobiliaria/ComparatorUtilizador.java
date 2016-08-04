import java.util.Comparator;

public class ComparatorUtilizador implements Comparator<Utilizador>{    
    public int compare(Utilizador u1,Utilizador u2){
        return u1.compareTo(u2);
    }
}