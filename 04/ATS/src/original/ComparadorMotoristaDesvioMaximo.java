
/**
 * Escreva a descrição da classe ComparadorMotoristaDesvioMaximo aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.util.Comparator;
public class ComparadorMotoristaDesvioMaximo implements Comparator<Motorista>
{
    public int compare(Motorista m1, Motorista m2){
        int aux = 0;
        double dMAX1 = 0;
        double dMAX2 = 0;
        try{
            dMAX1 = m1.maiorDesvio().getDesvio();
        }
        catch(NenhumaViagemException e){aux++;}
        
        try{
            dMAX2 = m2.maiorDesvio().getDesvio();
            //So chegamos neste patamar se a exceção não tiver sido lançada.
            //Se aux == 1 então o motorista m1 tem uma posição maior que m2.
            if(aux == 1) return 1;
        }
        catch(NenhumaViagemException e){aux++;}
        
        //Se chegarmos neste patamar e aux == 1, então o motorista m1 tem uma posição menor que m2.
        if(aux == 1) return (-1);
        else if(aux == 2) return 0; //Se nenhum dos motoristas tiver feito uma viagem, consideramos "iguais".
        
        return dMAX1 > dMAX2 ? (-1) : (dMAX1 < dMAX2 ? 1 : 0); 
    }
}
