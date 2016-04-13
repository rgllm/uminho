
import java.util.Objects;
import java.util.List;

public class Utilizador{

private String email;
private String nome;
private String password;
private String morada;
private String data_nascimento;

/* Métodos de instancia */

public String getEmail(){return email;}
public String getNome(){return nome;}
public String getPassword(){return password;}
public String getMorada(){return morada;}
public String getDataNasc(){return data_nascimento;}

public void setEmail(String e){email=e;}
public void setNome(String n){nome=n;}
public void setPassword(String p){password=p;}
public void setMorada(String m){morada=m;}
public void setDataNasc(String d){data_nascimento=d;}

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
        return "Utilizador{" + "email=" + email + ", nome=" + nome + ", password=" + password + ", morada=" + morada + ", data_nascimento=" + data_nascimento + '}';
    }


/*Construtores */

public Utilizador(){
    email="";
    nome="";
    password="";
    morada="";
    data_nascimento="";
}

public Utilizador(String e,String n,String p,String m,String d){
    email=e;
    nome=n;
    password=p;
    morada=m;
    data_nascimento=d;
}

public Utilizador (Utilizador x){
    email=x.getEmail();
    nome=x.getNome();
    password=getPassword();
    morada=getMorada();
    data_nascimento=getDataNasc();
}

/* Outros Métodos */

/*

/**
* Consultar a lista de todos os imóveis de um dado tipo (Terreno, Moradia, etc.) e até um certo preço.
*/
/*
public List<Imovel> getImovel(String classe, int preco){

}
*/
/**
* Consultar a lista de todos os imóveis habitáveis (até um certo preço).
*/
/*
public List<Habitavel> getHabitaveis(int preco){

}
*/
/**
* Obter um mapeamento entre todos os imóveis e respectivos vendedores.
*/
/*
public Map<Imovel, Vendedor> getMapeamentoImoveis(){

}
*/

}
