import java.util.*;

public class Comprador extends Utilizador{

    private Map<String,Imovel> favoritos;

    /*           Construtores         */
    public Comprador(){
        super("","","","","");
        favoritos=new TreeMap<>();
    }

    public Comprador(String email,String nome,String password,String morada,String data_nascimento){
        super(email,nome,password,morada,data_nascimento);
        this.favoritos=new TreeMap<>();
    }

    public Comprador(Comprador x){
        super(x.getEmail() , x.getNome() , x.getPassword() , x.getMorada() , x.getDataNasc() );
        this.favoritos=new TreeMap<>(x.getFavoritos());
    }

    public Map<String,Imovel> getFavoritos(){
        Map<String,Imovel> res = new TreeMap<>();
        for(Imovel i:favoritos.values()){
            res.put(String.valueOf(i.gerarIDImovel()),i.clone());
        }
        return res;
    }

    public void setFavoritos(Map<String,Imovel> s){
        favoritos.clear();
        for(Imovel i:s.values()){
            favoritos.put(i.getId(),i.clone());
        }
    }

    public void AddFavorito(Imovel i) throws ImovelFavoritoException{
        if(favoritos.containsKey(i.getId())){
            throw new ImovelFavoritoException("Este imóvel já é favorito.");
        }
        else{
            favoritos.put(i.getId(),i.clone());
        }
    }

    public Comprador clone(){
        return new Comprador(this);
    }

}
