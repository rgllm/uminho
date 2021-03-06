
import java.util.Objects;
import java.util.List;
import java.io.*;

public class Utilizador implements Comparable<Utilizador>, Serializable{

    private String email;
    private String nome;
    private String password;
    private String morada;
    private String data_nascimento;

    /*           Construtores         */
    public Utilizador(){
        email=new String("");
        nome=new String("");
        password=new String("");
        morada=new String("");
        data_nascimento=new String("");
    }

    public Utilizador(String email,String nome,String password,String morada,String data_nascimento){
        this.email=new String(email);
        this.nome=new String(nome);
        this.password=new String(password);
        this.morada=new String(morada);
        this.data_nascimento=new String(data_nascimento);
    }

    public Utilizador (Utilizador x){
        email=new String(x.getEmail());
        nome=new String(x.getNome());
        password=new String(x.getPassword());
        morada=new String(x.getMorada());
        data_nascimento=new String(x.getDataNasc());
    }

    /*      Métodos de instância    */

    public String getEmail(){return email;}
    public String getNome(){return nome;}
    public String getPassword(){return password;}
    public String getMorada(){return morada;}
    public String getDataNasc(){return data_nascimento;}

    public void setEmail(String email){this.email=new String(email);}
    public void setNome(String nome){this.nome=new String(nome);}
    public void setPassword(String password){this.password=new String(password);}
    public void setMorada(String morada){this.morada=new String(morada);}
    public void setDataNasc(String data_nascimento){this.data_nascimento=new String(data_nascimento);}

    public boolean equals(Object obj) {
        if (this == obj) {return true;}
        if (obj == null) {return false;}
        if (getClass() != obj.getClass()) {return false;}
        final Utilizador other = (Utilizador) obj;
        if (Objects.equals(this.email, other.email) &&
            Objects.equals(this.nome, other.nome) &&
            Objects.equals(this.password, other.password) &&
            Objects.equals(this.morada, other.morada) &&
            Objects.equals(this.data_nascimento, other.data_nascimento)) {
            return true;
        }
        return false;
    }

    public String toString() {
        return "Utilizador{" +
                "email=" + email +
                ", nome=" + nome +
                ", password=" +password +
                ", morada=" + morada +
                ", data_nascimento=" + data_nascimento + '}'; //Append?
    }

    public Utilizador clone(){
        return new Utilizador(this);
    }

    public int compareTo(Utilizador u){
        return this.getEmail().compareTo(u.getEmail());
    }

}
