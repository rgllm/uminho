/*
 * Escreva a descrição da classe Viagens aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.util.GregorianCalendar;
import java.io.Serializable;
public class Viagem implements Comparable<Viagem>, Serializable
{
    /** Variáveis de instância. */
    private Coordenada cinicial;
    private Coordenada cfinal;
    /** Tempo que demorou a realizar a viagem. */
    private double tempo;
    private String mail;
    private GregorianCalendar data;
    /** Preço real da viagem. */
    private double preco;
    /** Diferença entre o valor previsto e o preço real faturado. */
    private double desvio;
    
    /** Construtor Vazio. */
    public Viagem(){
        cinicial = new Coordenada();
        cfinal = new Coordenada ();
        tempo = preco = desvio = 0;
        mail = "";
        data = new GregorianCalendar();
    }
    
    /**
     * Construtor paramétrico.
     * @param Coordenada cinicial
     * @param Coordenada cfinal
     * @param double tempo
     * @param String mail
     * @param data
     * @param preco
     * @param desvio
     */
    public Viagem (Coordenada cinicial , Coordenada cfinal , double tempo , String mail, GregorianCalendar data, double preco, double desvio){
        this.cinicial = cinicial.clone();
        this.cfinal = cfinal.clone();
        this.tempo = tempo;
        this.mail = mail;
        this.data = (GregorianCalendar)data.clone();
        this.preco = preco;
        this.desvio = desvio;
    }
    
    /**
     * Construtor por cópia.
     * @param Viagem a
     */
    public Viagem(Viagem a){
        this.cinicial = a.getcinicial();
        this.cfinal = a.getcfinal();
        this.tempo = a.getTempo();
        this.mail = a.getMail();
        this.data = (GregorianCalendar)a.getData().clone();
        this.preco = a.getPreco();
        this.desvio = a.getDesvio();
    }
    
    /** Altera o valor da coordenada inicial. */
    public void setcinicial(Coordenada i){
        this.cinicial=i;
    }
    
    /** Altera o valor da coordenada final. */
    public void setcfinal(Coordenada i){
        this.cfinal=i;
    }
    
    /** Altera o valor do tempo. */
    public void setTempo(double i){
        this.tempo=i;
    }
    /** Altera o mail do ator. */
    public void setMail(String i){
        this.mail=i;
    }
    
    /**
     * @return Coordenada inicial
     */
    public Coordenada getcinicial(){
        return this.cinicial.clone();
    }
    
    /**
     * @return Coordenada final
     */
    public Coordenada getcfinal(){
        return this.cfinal.clone();
    }
    
    /**
     * @return Tempo
     */
    public double getTempo(){
        return this.tempo;
    }
    
    /**
     * @return Mail do Ator.
     */
    public String getMail(){
        return this.mail;
    }
    
    /**
     * Devolve a data da viagem.
     * @return
     */
    public GregorianCalendar getData(){
        return this.data;
    }
    
    /**
     * Devolve o preço da viagem.
     * @return
     */
    public double getPreco(){
        return this.preco;
    }
    
    /**
     * Devolve o desvio de preços da viagem.
     * @return
     */
    public double getDesvio(){
        return this.desvio;
    }
    
    /**
     * Detetermina o código de hash.
     * @return int
     */
    public int hashCode(){
        int hash = 5;
        long aux;        
        
        hash = 37 * hash + this.cinicial.hashCode();
        hash = 37 * hash + this.cfinal.hashCode();
        aux = Double.doubleToLongBits(this.tempo);
        hash = 37 * hash + (int)(aux^aux >>> 32);
        hash = 37 * hash + this.mail.hashCode();
        hash = 37*hash + this.data.hashCode();
        aux = Double.doubleToLongBits(this.preco);
        hash = 37 * hash + (int)(aux^aux >>> 32);
        aux = Double.doubleToLongBits(this.desvio);
        return 37 * hash + (int)(aux^aux >>> 32);
    }
    
    /** Metodo Compare. */
   public int compareTo(Viagem b){
       int r;
       r = cinicial.compareTo(b.getcinicial());
       if(r==0) {
		r = cfinal.compareTo(b.getcfinal());
	}
       if(r==0) {
           if(this.tempo<b.getTempo()) {
			r = -1;
		}
           if(this.tempo==b.getTempo()) {
			r = 0;
		} else {
			r = 1;
		}
        }
       if(r==0) {
		r = this.mail.compareTo(b.getMail());
	}
       if(r==0) {
		r = this.data.compareTo(b.getData());
	}
       if(r==0) {
           if(this.preco<b.getPreco()) {
			r = -1;
		}
           if(this.preco==b.getPreco()) {
			r = 0;
		} else {
			r = 1;
		}
        }
       if(r==0) {
           if(this.desvio<b.getDesvio()) {
			r = -1;
		}
           if(this.desvio==b.getDesvio()) {
			r = 0;
		} else {
			r = 1;
		}
        }
       
       return r;
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
        Viagem v = (Viagem) o;
        return v.getcinicial().equals(cinicial) && v.getcfinal().equals(cfinal) && 
               v.getMail().equals(mail) && v.getTempo() == tempo && this.data.equals(v.getData())
               && v.getPreco() == this.preco && v.getDesvio() == this.desvio;
    }
    
    /** Método clone. */
    public Viagem clone(){
        return new Viagem(this);
    }
    
    /**
     * Representação textual.
     * @return Informaçao da Viagem em String
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("Coordenada inicial: ").append(cinicial).append("\nCoordenada final: ").append(cfinal);
        s.append("\nTempo que demorou a viagem: ").append(String.format("%.2f",tempo));
        s.append("\nMail do ator: ").append(mail);
        s.append("\nData da viagem: ").append(this.data.get(GregorianCalendar.DAY_OF_MONTH)).append("-").append(this.data.get(GregorianCalendar.MONTH)).append("-").append(this.data.get(GregorianCalendar.YEAR));
        s.append("\nPreço real da viagem: ").append(String.format("%.2f",this.preco))
         .append("\nDesvio entre o valor previsto e o preço real faturado: ").append(String.format("%.2f",this.desvio));
        return s.toString();
    }
}
