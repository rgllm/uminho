import java.util.*;
import java.io.*;

@SuppressWarnings("unchecked")
public class Imoobiliaria implements Serializable{


    private boolean logado = false;
    private Utilizador userAtual = null;
    private Map<String,Utilizador> users;
    private Map<String,Imovel> imoveis;

    public Imoobiliaria(){
        users = new TreeMap<>();
        imoveis = new TreeMap<>();
    }

    public Imoobiliaria(Imoobiliaria imoobiliaria){
        this.logado=imoobiliaria.isLogado();
        this.userAtual=imoobiliaria.getUserAtual().clone();
        this.users=imoobiliaria.getUsers();
        this.imoveis=imoobiliaria.getImoveis();
    }

    //Gets e Sets
    public boolean isLogado() {return logado;}
    public Utilizador getUserAtual() {return userAtual;}
    public Map<String,Utilizador> getUsers(){
        Map<String,Utilizador> res = new TreeMap<>();
        for(Map.Entry<String,Utilizador> u:users.entrySet())
            res.put(u.getValue().getEmail(),u.getValue().clone());
        return res;
    }
    public Map<String,Imovel> getImoveis(){
        Map<String,Imovel> res = new TreeMap<>();
        for(Map.Entry<String,Imovel> im:imoveis.entrySet()){
            res.put(im.getKey(),im.getValue().clone());
        }
        return res;
    }

    public void setLogado(boolean logado) {this.logado = logado;}
    public void setUserAtual(Utilizador userAtual) {this.userAtual = userAtual;}
    public void setUsers(Map<String,Utilizador> nUsers){
        users.clear();
        for(Map.Entry<String,Utilizador> u:nUsers.entrySet())
            users.put(u.getKey(),u.getValue().clone());
    }
    public void setImoveis(Map<String,Imovel> nImoveis){
        imoveis.clear();
        for(Map.Entry<String,Imovel> im:nImoveis.entrySet())
            imoveis.put(im.getKey(),im.getValue().clone());
   }


    /**
    * Outros métodos
    */


    /**
    * Aplicação deverá estar pré-populada com conjunto de dados
    * significativos, que permita testar toda a aplicação no dia da entrega
    */
    public static Imoobiliaria leObj(String fich) throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fich));
      
        Imoobiliaria te= (Imoobiliaria) ois.readObject();
        
        ois.close();
        return te;
    }

    /**
    * Verifica se um determinado utilizador já existe.
    */
    public boolean existeUtilizador (Utilizador u){

        if(users.containsKey(u.getEmail())) return true;
        else return false;
    }

    /**
    * Registar um utilizador, quer seja vendedor, quer seja comprador.
    */
    public void registarUtilizador (Utilizador u) throws UtilizadorExistenteException{

        if(existeUtilizador(u)==true){
            throw new UtilizadorExistenteException("O utilizador já existe.");
        }
        else{
            users.put(u.getEmail(),u.clone());
        }
    }

    /**
    * Validar o acesso à aplicação utilizando as credenciais (email e password)
    */
    public void iniciaSessao (String email , String password ) throws SemAutorizacaoException{

        if(!users.containsKey(email)){
            throw new SemAutorizacaoException("O email não está registado.");
        }
        else if(!users.get(email).getPassword().equals(password)){
            throw new SemAutorizacaoException("A password está incorreta.");
        }
        else{
            logado=true;
            userAtual=users.get(email);
        }
    }

    /**
    * Guarda o estado atual do programa num ficheiro.
    */
    public void gravaObj(String fich) throws IOException { 
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fich)); 
        oos.writeObject(this); 
        
        oos.flush(); 
        oos.close(); 
    } 

    /**
    * Fechar a sessão do utilizador e guarda o estado atual do programa.
    */
    public void fechaSessao (){
        logado=false;
        userAtual=null;
    }

    /**
    * Colocar um imóvel à venda;
    */
    public void registaImovel(Imovel im) throws ImovelExisteException , SemAutorizacaoException{

        if(logado==false){
             throw new SemAutorizacaoException("Precisa de estar logado para registar um imóvel.");
        }
        if(userAtual instanceof Comprador){
                throw new SemAutorizacaoException("Só os vendedores podem colocar um imóvel à venda.");
        }
        if(imoveis.containsKey(im.getId())){
             throw new ImovelExisteException("Este imóvel já se encontra à venda.");
        }
        else{
            imoveis.put(im.getId(),im.clone());
            Vendedor novo = (Vendedor) userAtual;
            novo.paraVenda(im);
        }
    }

    /**
    * Visualizar uma lista com as datas (e emails, caso exista essa informação) das
    * 10 últimas consultas aos imóveis que tem para venda
    */
    /*public List<Consulta> getConsultas() throws SemAutorizacaoException{

       if(logado==false){
             throw new SemAutorizacaoException("Precisa de estar logado para registar um imóvel.");
        }
        else{
            return null;
         }
    }

     /**
    * Verifica se um determinado imóvel já existe a partir do seu ID.
    */
    public boolean existeImovel (String idImovel){

        if(imoveis.containsKey(idImovel)) return true;
        else return false;
    }

    /**
    * Alterar o estado de um imóvel, de acordo com as acções feitas sobre ele
    */
    public void setEstado(String idImovel , String estado) throws ImovelInexistenteException , SemAutorizacaoException , EstadoInvalidoException{

        if(logado==false){
            throw new SemAutorizacaoException("Precisa de estar logado.");
        }
        if(userAtual instanceof Comprador){
            throw new SemAutorizacaoException("Só os vendedores podem alterar o estado de um imóvel.");
        }
        if(!estado.equals("Para_Venda") && !estado.equals("Reservado") && !estado.equals("Vendido") && !estado.equals("Outro")){
            throw new EstadoInvalidoException("O estado pretendido é inválido.");
        }
        if(existeImovel(idImovel)==false){
            throw new ImovelInexistenteException("O imóvel pretendido não existe.");
        }
        else{
            imoveis.get(idImovel).setEstado(Estado_Imovel.valueOf(estado));
        }
    }

    /**
    * Obter um conjunto com os códigos dos imóveis
    * mais consultados (ou seja, com mais de N consultas)
    */
    /*public Set<String> getTopImoveis(int n){
        return null;
    } */

    /**
    * Consultar a lista de todos os imóveis de um dado tipo (Terreno, Moradia, etc.) e até um certo preço.
    */
    public List<Imovel> getImovel(String classe, int preco){
        List<Imovel> res = new ArrayList<>();
        for(Imovel im:imoveis.values()){
            if(im.getClass().getSimpleName().equals(classe))
                if(im.getPreco()<=preco)
                    res.add(im.clone());
        }
        return res;
    }

    /**
    * Consultar a lista de todos os imóveis habitáveis (até um certo preço).
    */

    public List<Habitavel> getHabitaveis(int preco){
        List<Habitavel> res = new ArrayList<>();

        for(Imovel im:imoveis.values()){
            if(im instanceof Habitavel)
                if(im.getPreco() <= preco){
                    res.add((Habitavel) im.clone());
            }
        }
        return res;
    }

    /**
    * Obter um mapeamento entre todos os imóveis e respectivos vendedores.
    */
    public Map<Imovel, Vendedor> getMapeamentoImoveis(){
        Map<Imovel,Vendedor> res = new TreeMap();
        for(Utilizador u: users.values()){
            if(u instanceof Vendedor){
            Vendedor v = (Vendedor) u;
            for(Imovel im: v.getPortfolio())
                res.put(im,v);
            }
       }
      return res;
    }

    /**
    * Marcar um imóvel como favorito (só disponível para compradores).
    **/
    /*public void setFavorito(String idImovel) throws SemAutorizacaoException {

           if(logado==false){
                throw new SemAutorizacaoException("Não tem permissões para marcar um imóvel como favorito.");
           }
           if(userAtual instanceof Vendedor){
                throw new SemAutorizacaoException("Só os compradores podem marcar um imóvel como favorito.");
           }
           else{
                    if(existeImovel(idImovel) == false){
                         throw new ImovelInexistenteException("O imóvel não existe");
                    }
                  else{
                        Comprador c = (Comprador) userAtual;
                        try{
                            c.favorito(imoveis.get(idImovel));
                        }
                        catch(ImovelFavoritoException exc){
                            throw new SemAutorizacaoException(exc.toString());
                        }
                  }
           }
    }*/


    /**
    * Consultar imóveis favoritos ordenados por preço
    **/
    public TreeSet<Imovel> getFavoritos() throws SemAutorizacaoException{
        TreeSet<Imovel> f = new TreeSet<>();
        if(logado==false){
            throw new SemAutorizacaoException("Tem que iniciar a sessão para marcar um imóvel como favorito.");
        }
        if(userAtual instanceof Vendedor){
            throw new SemAutorizacaoException("Só os compradores podem marcar um imóvel como favorito!");
        }
        else{
            Comprador c = (Comprador) userAtual;
            for(Imovel i: c.getFavoritos().values()){
                f.add(i.clone());
            }
        }
        return f;
    }

    public Imoobiliaria clone(){
        return new Imoobiliaria(this);
    }

       /*public String toString() {
        StringBuilder sb = new StringBuilder("--- Imoobiliaria ---\n");
        for(Utilizador u : this.users.values())
        sb.append(u.toString() + "\n");
        for(Imovel i : this.imoveis.values())
        sb.append(i.toString() + "\n");
        return sb.toString();
    } */
}