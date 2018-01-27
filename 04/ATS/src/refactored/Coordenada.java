import java.util.*;
/*
 * Escreva a descrição da classe Coordenada aqui.
 * 
 * @author (seu nome) 
 * @version v1(02052017)
 */
import java.io.Serializable;
public class Coordenada implements Serializable
{
    /** Variáveis de instância. */
    private int coorX;
    private int coorY;
    
    /** Construtor Vazio. */
    public Coordenada(){
        coorX=0;
        coorY=0;
    }
    
    /**
     * Construtor paramétrico.
     * @param int x
     * @param int y
     */
    public Coordenada(int x, int y){
        this.coorX=x;
        this.coorY=y;
    }
    
    /**
     * Construtor por cópia.
     * @param Coordenada a
     */
   public Coordenada(Coordenada a){
        this.coorX=a.getX();
        this.coorY=a.getY();
    }
    
      /**
     * @return variavel coorX
     */
    public int getX(){
        return this.coorX;
   }
    
   /**
     * @return variavel coorY
     */
    public int getY(){
        return this.coorY;
   }
   
   /**
     * Detetermina o código de hash.
     * @return int
     */
    public int hashCode(){
        int hash=6;
        
        hash = 37 * hash + this.coorX;
        return 37 * hash + this.coorY;
    }
   
   /** Metodo Compare. */
   public int compareTo(Coordenada b){
       if(this.coorX==b.getX()){
           if(this.coorY==b.getY()) {
			return 0;
		}
           if(this.coorY<b.getY()) {
			return 1;
		} else {
			return -1;
		}
       }
       if(this.coorX<b.getX()) {
		return 1;
	} else {
		return -1;
	}
    }
   
   /**
    * Comparação com outro Objeto o
    * @param Object o
    * @return boolean a indicar se é igual ao não a 'o'.
    */
   public boolean equals(Object o) {
       if(o==this) {
		return true;
	}
       if(o==null || o.getClass() != getClass()) {
		return false;
	}
       Coordenada v = (Coordenada) o;
       return v.getX()==coorX && v.getY()==coorY;
   }
   
   /** Método clone. */
   public Coordenada clone(){
       return new Coordenada(this);
   }
   
   /**
     * Representação textual.
     * @return Informaçao da Coordenada em String
     */
   public String toString(){
       StringBuilder s = new StringBuilder();
       s.append("Coordenada X: ").append(coorX).append(" Coordenada Y: ").append(coorY);
       return s.toString();
   }
   
   public double distancia(Coordenada b){
       return  Math.sqrt( Math.pow( coorX - b.getX(),2 ) + Math.pow( coorY - b.getY(),2 ) );
   }
}
