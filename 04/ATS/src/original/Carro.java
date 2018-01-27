
/**
 * Write a description of class Carro here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.io.Serializable;
public class Carro extends Veiculo implements Serializable{
    
    /**
     * Construtor vazio. Cria uma instância de Carro.
     */
    public Carro(){
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
    public Carro(int velocidademedia, double precobase, int fiabilidade, String matricula, Coordenada coordenadas, boolean ocupado){
        super(velocidademedia,precobase,fiabilidade,matricula,coordenadas,4,ocupado);
    }
    
    /**
     * Construtor por cópia.
     * @param c
     */
    public Carro(Carro c){
        super(c);
    }
    
    /**
     * Implementação de clone.
     * @return
     */
    public Carro clone() {
        return new Carro(this);
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
        Carro c = (Carro) o;
        return super.equals(o);
    }    

    public String toString(){
        return super.toString();
    }
}
