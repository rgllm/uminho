
/**
 * Escreva a descrição da classe Cliente aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.util.Set;
import java.util.TreeSet;
import java.io.Serializable;
public class Cliente extends Ator implements Serializable
{
    //variáveis de instância
    private double moneySpent; //Quantidade de dinheiro total gasto no servico UMeR
    
    /**
     * Construtor Vazio
     */
    public Cliente(){
        super();
        this.moneySpent = 0;
    }
    
    /**
     * Construtor por cópia
     * @param c
     */
    public Cliente(Cliente c){
        super(c);
        this.moneySpent = c.getMS();
    }
    
    /**
     * Construtor paramétrico
     * @param email
     * @param nome
     * @param pw
     * @param morada
     * @param dDN
     * @param historico
     * @param ms
     */
    public Cliente(String email, String nome, String pw, String morada, String dDN, Set<Viagem> historico, double ms){
        super(email,nome,pw,morada,dDN,historico);
        this.moneySpent = ms;
    }
    
    /**
     * Construtor paramétrico parcial.
     * @param email
     * @param nome
     * @param pw
     * @param morada
     * @param dDN
     */
    public Cliente(String email, String nome, String pw, String morada, String dDN){
        super(email,nome,pw,morada,dDN, new TreeSet<Viagem>());
        this.moneySpent = 0;
    }
    
    /**
     * Devolve a quantidade de dinheiro gasto pelo cliente.
     * @return
     */
    public double getMS(){
        return this.moneySpent;
    }
    
    /**
     * Define a quantidade de dinheiro gasto pelo cliente.
     * @param ms
     */
    public void setMS(double ms){
        this.moneySpent = ms;
    }
    //Métodos usuais
    /**
     * Igualdade com outro objeto.
     * @param o
     * @return
     */
    public boolean equals(Object o){
        if(o == this) return true;
        
        if(o == null || (o.getClass() != this.getClass())) return false;
        
        Cliente c = (Cliente) o;
        
        return super.equals(c) && this.moneySpent == c.getMS();
    }
    
    /**
     * Determina o código de hash do objeto.
     * @return
     */
    public int hashCode(){
        int hash = 7;
        
        hash = 37*hash + super.hashCode();
        long dbTol = Double.doubleToLongBits(this.moneySpent);
        hash = 37*hash + (int)(dbTol ^ (dbTol >>> 32));
        
        return hash;
    }
    
    /**
     * Representação no formato texto.
     * @return
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        
        s.append(super.toString())
         .append("Montante de dinheiro utilizado no serviço UMeR: ").append(String.format("%.2f",this.moneySpent)).append("\n");
        
         return s.toString();
    }
    
    /**
     * Criação de uma cópia
     * @return
     */
    public Cliente clone(){
        return new Cliente(this);
    }
    
    /**
     * Implementação da ordem natural entre instâncias de Cliente.
     * @param c
     */
    public int compareTo(Cliente c){
        int r = super.compareTo(c);
        
        if(r == 0) r = this.moneySpent == c.getMS() ? 0 : (this.moneySpent > c.getMS() ? 1 : (-1));
        
        return r;
    }
}
