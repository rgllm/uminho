
/**
 * Escreva a descrição da classe ComparadorDesvio aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.util.Comparator;
public class ComparadorDesvio implements Comparator<Viagem>
{
    public int compare(Viagem v1, Viagem v2){
        double d1 = v1.getDesvio();
        double d2 = v2.getDesvio();
        
        return d1 > d2 ? 1 : (d1 < d2 ? (-1) : 0);
    }
}
