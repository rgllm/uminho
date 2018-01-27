
/**
 * Write a description of class Carrinha here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.Serializable;
public class Carrinha extends Veiculo implements Serializable{
    
    /**
     * Construtor vazio. Cria uma instância de Carrinha.
     */
    public Carrinha(){
        super();
    }
    
    /**
     * Construtor por parâmetro.
     * @param velocidademedia
     * @param precobase
     * @param fiabilidade
     * @param matricula
     * @param coordenadas
     * @param ocupado
     */
    public Carrinha(int velocidademedia, double precobase, int fiabilidade, String matricula, Coordenada coordenadas, boolean ocupado){
        super(velocidademedia,precobase,fiabilidade,matricula,coordenadas,9,ocupado);
    }

    /**
     * Construtor por cópia.
     * @param c
     */
    public Carrinha(Carrinha c){
        super(c);
    }
    
    /**
     * Implementação de clone.
     * @return
     */
    public Carrinha clone() {
        return new Carrinha(this);
    }
    
    /**
     * Compara a igualdade com outro objecto.
     * @param o
     * @return 
     */
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }
        if(o == null || o.getClass() != this.getClass()) {
            return false;
        }
        Carrinha c = (Carrinha) o;
        return super.equals(o);
    }    
    
    public String toString(){
        return super.toString();
    }
}