
/**
 * Escreva a descrição da classe Atores aqui.
 * 
 * @author (seu nome) 
 * @version (número de versão ou data)
 */
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.io.Serializable;
import java.util.Iterator;
import java.util.GregorianCalendar;
import java.util.stream.Collectors;
public abstract class Ator implements Comparable<Ator>, Serializable
{
    // variáveis da instancia associadas aos atores do sistema
    private String email; //Identificador único(Chave).
    private String nome; //Nome
    private String pw; //Password
    private String morada; //Morada
    private String dataDeNascimento; //Data de nascimento
    private Set<Viagem> historico; //Histórico de viagens realizadas.
    
    /**
     * Construtor Vazio
     */
    public Ator(){
        this.email = this.nome = this.pw = this.morada = this.dataDeNascimento = "";
        this.historico = new TreeSet<>();
    }
    
    /**
     * Construtor por cópia
     * @param a
     */
    public Ator(Ator a){
        this.email = a.getMail();
        this.nome = a.getNome();
        this.pw = a.getPassword();
        this.morada = a.getMorada();
        this.dataDeNascimento = a.getDataDeNascimento();
        this.historico = a.getHistorico();
    }
    
    /**
     * Construtor paramétrico
     * @param email
     * @param nome
     * @param pw
     * @param morada
     * @param dDN
     * @param historico
     */
    public Ator(String email, String nome, String pw, String morada, String dDN, Set<Viagem> hist){
        this.email = email;
        this.nome = nome;
        this.pw = pw;
        this.morada = morada;
        this.dataDeNascimento = dDN;
        this.historico = new TreeSet<>();
        
        for(Viagem v : hist)
            this.historico.add(v.clone());
    }
    
    /**
     * Devolve o email do ator
     * @return String email
     */
    public String getMail(){
        return this.email;
    }
    
    /**
     * Devolve o nome do ator
     * @return String nome
     */
    public String getNome(){
        return this.nome;
    }
    
    /**
     * Devolve a password do ator
     * @return String password
     */
    public String getPassword(){
        return this.pw;
    }
    
    /**
     * Devolve a morada do ator
     * @return String morada
     */
    public String getMorada(){
        return this.morada;
    }
    
    /**
     * Devolve a data de nascimento do ator
     * @return String data de nascimento
     */
    public String getDataDeNascimento(){
        return this.dataDeNascimento;
    }
    
    /**
     * Devolve uma cópia do histórico de viagens do objeto.
     * @return Set<Viagem>
     */
    public Set<Viagem> getHistorico(){
        Set<Viagem> res = new TreeSet<>();
        
        this.historico.forEach(v -> res.add(v.clone()));
        
        return res;
    }
    
    /**
     * Adiciona um novo registo de viagem.
     * @param rv
     */
    public void registaViagem(Viagem rv){
        this.historico.add(rv.clone());
    }
    
    /**
     * Constroí uma lista das viagens efetuadas numa data pertencente ao intervalo de tempo definido por quem invoca o método.
     * @param inicio
     * @param fim
     * @return
     */
    public List<Viagem> viagensEntreDatas(GregorianCalendar inicio, GregorianCalendar fim) throws InvalidIntervalException{
        if(inicio.compareTo(fim) == 1) throw new InvalidIntervalException();
        else{
            return this.historico.stream()
                                 .filter(v -> v.getData().compareTo(inicio) >= 0 && v.getData().compareTo(fim) <= 0)
                                 .map(Viagem :: clone)
                                 .collect(Collectors.toList());
        }
    }
    
    /**
     * Devolve a viagem com o maior desvio de preços no historico de viagens.
     * @return
     */
    public Viagem maiorDesvio() throws NenhumaViagemException{
        if(this.historico.size() == 0) throw new NenhumaViagemException();
        else return this.historico.stream()
                                  .max(new ComparadorDesvio())
                                  .get()
                                  .clone();
    }
    
    //Métodos usuais
    /**
     * Igualdade com outro objeto
     * @param Object o
     * @return boolean indicando se é igual ou não
     */
    public boolean equals(Object o){
        if(o == this) return true;
        
        if(o == null || (o.getClass() != this.getClass())) return false;
        
        Ator at = (Ator) o;
        
        if(this.historico.size() != at.getHistorico().size()) return false;
        
        return (this.hashCode() == at.hashCode()) && this.email.equals(at.getMail()) && this.nome.equals(at.getNome()) && this.pw.equals(at.getPassword()) && this.morada.equals(at.getMorada()) 
                                                  && this.dataDeNascimento.equals(at.getDataDeNascimento()) && this.historico.containsAll(at.getHistorico());
    }
    
    /**
     * Detetermina o código de hash
     * @return int
     */
    public int hashCode(){
        int hash = 7;
        
        hash = 37*hash + this.email.hashCode();
        hash = 37*hash + this.nome.hashCode();
        hash = 37*hash + this.pw.hashCode();
        hash = 37*hash + this.morada.hashCode();
        hash = 37*hash + this.dataDeNascimento.hashCode();
        for(Viagem v : this.historico)
            hash = 37*hash + v.hashCode();
        
        return hash;
    }
    
    /**
     * Representação textual
     * @return String
     */
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("E-mail: ").append(this.email).append("\n")
         .append("Nome: ").append(this.nome).append("\n")
         .append("Password: ").append(this.pw).append("\n")
         .append("Morada: ").append(this.morada).append("\n")
         .append("Data de nascimento: ").append(this.dataDeNascimento);
        
        this.historico.forEach(v -> s.append(v.toString()).append("\n"));
        
        return s.toString();
    }
    
    /**
     * Cria uma cópia
     * @return Ator
     */
    public abstract Ator clone();
    
    /**
     * Define a ordem natural entre atores do sistema.
     * @return int
     */
    public int compareTo(Ator a){
        int r;
        r = this.email.compareTo(a.getMail());
        if(r == 0) r = this.nome.compareTo(a.getNome());
        if(r == 0) r = this.pw.compareTo(a.getPassword());
        if(r == 0) r = this.morada.compareTo(a.getMorada());
        if(r == 0) r = this.dataDeNascimento.compareTo(a.getDataDeNascimento());
        if(r == 0){
             Iterator<Viagem> it1 = this.historico.iterator();
             Iterator<Viagem> it2 = a.getHistorico().iterator();
             while(r == 0 && it1.hasNext() && it2.hasNext()){
                      Viagem v1 = it1.next();
                      Viagem v2 = it2.next();
                      r = v1.compareTo(v2);
                 }
             if(r == 0 && it1.hasNext()) r = 1;
             else if(r == 0 && it2.hasNext()) r = (-1);
          }
      
        return r;
    }
}
