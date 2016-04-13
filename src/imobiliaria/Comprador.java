import java.util.TreeSet;

public class Comprador extends Utilizador{
    private TreeSet<Imovel> favoritos;

    /*           Construtores         */
    public Comprador(){
        super("","","","","");
        favoritos=null;
    }

    public Comprador(String email,String nome,String password,String morada,String data_nascimento,TreeSet<Imovel> favoritos){
        super(email,nome,password,morada,data_nascimento);
        this.favoritos=new TreeSet<Imovel>(favoritos);
    }

    public Comprador(Comprador x){
        super(x.getEmail() , x.getNome() , x.getPassword() , x.getMorada() , x.getDataNasc() );
        this.favoritos=new TreeSet<Imovel>(x.getFavoritos());
    }

    /*      Métodos de instância    */
    public TreeSet<Imovel> getFavoritos(){return favoritos;}

    public void setFavoritos(TreeSet<Imovel> favoritos){
        this.favoritos=(TreeSet<Imovel>)favoritos.clone();
    }
    /**
    * Marcar um imóvel como favorito
    */
    /*
    public void setFavorito(String idImovel) throws ImovelInexistenteException, SemAutorizacaoException{

    }
    */
    /**
    * Consultar imóveis favoritos ordenados por preço
    */
    /*
    public TreeSet<Imovel> getFavoritos() throws SemAutorizacaoException{

    }
    */

}
