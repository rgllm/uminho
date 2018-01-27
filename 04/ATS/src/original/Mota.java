
/**
 * Write a description of class Mota here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.Serializable;
public class Mota extends Veiculo implements Serializable{
    /**
     * Construtor vazio. Cria uma instância de Mota.
     */
    public Mota(){
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
    public Mota(int velocidademedia, double precobase, int fiabilidade, String matricula, Coordenada coordenadas, boolean ocupado){
        super(velocidademedia,precobase,fiabilidade,matricula,coordenadas,1,ocupado);
    }
    
    /**
     * Construtor por cópia.
     * @param c
     */
    public Mota(Mota c){
        super(c);
    }
    
    /**
     * Implementação de clone.
     * @return
     */
    public Mota clone() {
        return new Mota(this);
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
        Mota c = (Mota) o;
        return super.equals(o);
    }
    
    public String toString(){
        return super.toString();
    }
}