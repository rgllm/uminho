/*
 * Classe dos moradores
 */


package billsplitter;
import java.util.*;

public class Utilizador {
    private String email;
    private String nome;
    private Date data_chegada;
    private Date data_saida;
    private String password;
    private float saldo;
    private int casa;
    private HashMap<String,Float> situacoesIrregulares;

    public Utilizador(String email, String nome, Date data_chegada, Date data_saida, String password, float saldo, int casa, HashMap<String,Float> situacoesIrregulares) {
        this.email = email;
        this.nome = nome;
        this.data_chegada = data_chegada;
        this.data_saida = data_saida;
        this.password = password;
        this.saldo = saldo;
        this.casa = casa;
        this.situacoesIrregulares=situacoesIrregulares;
    }
    
    public Utilizador(String email, String nome, Date data_chegada, Date data_saida, String password, float saldo, int casa) {
        this.email = email;
        this.nome = nome;
        this.data_chegada = data_chegada;
        this.data_saida = data_saida;
        this.password = password;
        this.saldo = saldo;
        this.casa = casa;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String,Float> getSituacoesIrregulares(){
        return situacoesIrregulares;
    }

    public int getCasa() {
        return casa;
    }

    public void setCasa(int casa) {
        this.casa = casa;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData_chegada() {
        return data_chegada;
    }

    public void setData_chegada(Date data_chegada) {
        this.data_chegada = data_chegada;
    }

    public Date getData_saida() {
        return data_saida;
    }

    public void setData_saida(Date data_saida) {
        this.data_saida = data_saida;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getSaldo() {
        return saldo;
    }

    public void setSaldo(float saldo) {
        this.saldo = saldo;
    }



}