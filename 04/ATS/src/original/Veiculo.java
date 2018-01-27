
/**
 * Write a description of class Veiculo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.util.List;
import java.io.Serializable;
public abstract class Veiculo implements Comparable<Veiculo>, Serializable{
    /** Velocidade média por km. */
    private int velocidademedia;
    /** Preço base por km. */
    private double precobase; 
    /** Fiabilidade. */
    private int fiabilidade;
    /** Matrícula do veículo. */
    private String matricula;
    /** Coordenadas onde o Veiculo se encontra */
    private Coordenada coordenadas;
    /** Número de lugares. */
    private int lugares;
    /** Ocupado*/
    private boolean ocupado;
    
    /**
     * Construtor vazio. Cria uma instância de Veiculo.
     */
    public Veiculo(){
        this.velocidademedia = 0;
        this.precobase = 0;
        this.fiabilidade = 0;
        this.matricula = "n/a";
        this.coordenadas = new Coordenada();
        this.lugares = lugares;
        this.ocupado = false;
    }
    
    /**
     * Construtor por parâmetro.
     * @param velocidademedia
     * @param precobase
     * @param fiabilidade
     * @param matricula
     * @param coordenadas
     * @param lugares
     * @param ocupado
     */
    public Veiculo(int velocidademedia, double precobase, int fiabilidade, String matricula, Coordenada coordenadas, int lugares, boolean ocupado){
        this.velocidademedia = velocidademedia;
        this.precobase = precobase;
        this.fiabilidade = fiabilidade;
        this.matricula = matricula;
        this.coordenadas = new Coordenada(coordenadas);
        this.lugares = lugares;
        this.ocupado = ocupado;
    }
    
    /**
     * Construtor por cópia.
     * @param v
     */
    public Veiculo(Veiculo v){
        this.velocidademedia = v.getVelocidadeMedia();
        this.precobase = v.getPrecoBase();
        this.fiabilidade = v.getFiabilidade();
        this.matricula = v.getMatricula();
        this.coordenadas = new Coordenada(v.getCoordenadas());
        this.lugares = v.getLugares();
        this.ocupado = v.getOcupado();
    }
    
    /**
     * Obter a velocidade média por km do veículo.
     * @return
     */
    public int getVelocidadeMedia(){
        return this.velocidademedia;
    }
    
    /**
     * Obter o preço base por km do veículo.
     * @return
     */
    public double getPrecoBase(){
        return this.precobase;
    }
    
    /**
     * Obter a fiabilidade do veículo.
     * @return
     */
    public int getFiabilidade(){
        return this.fiabilidade;
    }   

    /**
     * Obter a matrícula do veículo.
     * @return
     */
    public String getMatricula(){
        return this.matricula;
    }
    
    /**
     * Obter as coordenadas do veículo.
     * @return 
     */
    public Coordenada getCoordenadas(){
        return this.coordenadas.clone();
    }
    
    /**
     * Obter os lugares do veículo.
     * @return
     */
    public int getLugares(){
        return this.lugares;
    }
    
    /**
     * Obter a ocupação do veículo.
     * @return
     */
    public boolean getOcupado(){
        return this.ocupado;
    }
    
    /**
     * Definir a velocidade média/km do veículo.
     * @param vm
     */
    public void setVelocidadeMedia(int velocidademedia){
        this.velocidademedia = velocidademedia;
    }
    
    /**
     * Definir o preço base por km do veículo.
     * @param pb
     */
    public void setPrecoBase(double precobase){
        this.precobase = precobase;
    }
    
    /**
     * Definir a fiabilidade do veículo.
     * @param fiabilidade
     */
    public void setFiabilidade(int fiabilidade){
        this.fiabilidade = fiabilidade;
    }
    
    /**
     * Definir a matrícula do veículo.
     * @param matricula
     */
    public void setMatricula(String matricula){
        this.matricula = matricula;
    }
    
    /**
     * Definir as coordenadas do veículo
     * @param coordenadas
     */
    public void setCoordenadas(Coordenada coordenadas){
        this.coordenadas = new Coordenada(coordenadas);
    }
    
        /**
     * Definir a ocupação do veículo.
     * @param ocupado
     */
    public void setOcupado(boolean ocupado){
        this.ocupado = ocupado;
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
        Veiculo v = (Veiculo) o;
        return v.getMatricula().equals(this.matricula) && v.getVelocidadeMedia() == this.velocidademedia 
                && v.getPrecoBase() == this.precobase && v.getFiabilidade() == this.fiabilidade
                && this.coordenadas.equals(v.getCoordenadas()) && v.getLugares() == this.lugares
                && v.getOcupado() == this.ocupado;
    }
   
    /**
     * Implementação de clone.
     * @return
     */
    public abstract Veiculo clone();
    
    /**
     * Devolve uma representação em formato de texto.
     * @return 
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Matrícula: ").append(this.matricula).append("\n");
        sb.append("Velocidade Média/km: ").append(this.velocidademedia).append("km/h").append("\n");
        sb.append("Preço Base: ").append(this.precobase).append("€").append("\n");
        sb.append("Fiabilidade: ").append(this.fiabilidade).append("\n");
        sb.append("Lugares: ").append(this.lugares).append("\n");
        sb.append("Coordenadas: ").append(this.getCoordenadas().toString()).append("\n");
        sb.append("Ocupado: ").append(this.ocupado).append("\n");
        return sb.toString();
    }
    
    /**
     * Código de hash
     * @return hashcode.
     */
    public int hashCode() {
        int hash = 2;
        long aux;
        
        hash = 37 * hash + this.velocidademedia;
        aux = Double.doubleToLongBits(this.precobase);
        hash = 37 * hash + (int)(aux^(aux >>> 32));
        hash = 37 * hash + this.fiabilidade;
        hash = 37 * hash + this.matricula.hashCode();
        hash = 37 * hash + this.lugares;
        hash = 37 * hash + (this.ocupado ? 1 : 0);
        
        return hash;
    }
    
    /**
     * Implementação da ordem natural de comparação de instâncias de Veículo.
     *
    */
    public int compareTo(Veiculo v) {
        return v.getMatricula().compareTo(this.matricula);
    }
}
