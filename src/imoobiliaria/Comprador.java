import java.util.*;

public class Comprador extends Utilizador{

    private ArrayList<Imovel> favoritos;

    /*           Construtores         */
    public Comprador(){
        super("","","","","");
        favoritos=new ArrayList<>();
    }

    public Comprador(String email,String nome,String password,String morada,String data_nascimento,ArrayList<Imovel> favoritos){
        super(email,nome,password,morada,data_nascimento);
        this.favoritos=new ArrayList<>(favoritos);
    }

    public Comprador(Comprador x){
        super(x.getEmail() , x.getNome() , x.getPassword() , x.getMorada() , x.getDataNasc() );
        this.favoritos=new ArrayList<Imovel>(x.getFavoritos());
    }

/*
          Métodos de instância
    public List<Imovel> getFavoritos(){return favoritos;}

    public void setFavoritos(ArrayList<Imovel> favoritos){
        this.favoritos=(ArrayList<Imovel>)favoritos.clone();
    }

*/
}
