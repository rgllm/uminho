import java.util.*;

public class Imoobiliaria{


    private boolean logado = false;
    private Utilizador userAtual = null;
    private Map<String,Utilizador> users;
    private Map<String,Imovel> imoveis;


    /**
    * Construtores
    */
    public Imoobiliaria(){
        users = new TreeMap<>();
        imoveis = new TreeMap<>();
    }

    /**
    * Gets
    */
    public boolean isLogado() {return logado;}
    public Utilizador getUserAtual() {return userAtual;}
    public Map<String,Utilizador> getUsers(){
        Map<String,Utilizador> res = new TreeMap<>();
        for(Map.Entry<String,Utilizador> u:users.entrySet())
            res.put(u.getValue().getEmail(),u.getValue().clone()); //TODO
        return res;
    }
    public Map<String,Imovel> getImoveis(){
        Map<String,Imovel> res = new TreeMap<>();
        for(Map.Entry<String,Imovel> im:imoveisRegistados.entrySet()){
            res.put(im.getKey(),im.getValue().clone());
        }
        return res;
    }

    /**
    * Sets
    */
    public void setLogado(boolean logado) {this.logado = logado;}
    public void setUserAtual(Utilizador userAtual) {this.userAtual = userAtual;}
    public void setUtilizadoresRegistados(Map<String,Utilizador> nUsers){
        users.clear();
        for(Map.Entry<String,Utilizador> u:nUsers.entrySet())
            users.put(u.getKey(),u.getValue().clone());
    }
    public void setImoveisRegistados(Map<String,Imovel> nImoveis){
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
    public static void initApp (){

    }

    /**
    * Verifica se um utilizador já existe.
    */
    public boolean existeUtilizador (Utilizador u){

        if(users.containsKey(u.getEmail())) return true;
        else return false;
    }

    /**
    * Registar um utilizador, quer vendedor, quer comprador.
    */
    public void registarUtilizador (Utilizador u) throws UtilizadorExistenteException{

        if(existeUtilizador(u)==false){ //TODO
            throw new UtilizadorExistenteException("Utilizador Existente");
        }
        else{
            users.put(u.getEmail(),u.clone()); // Ver melhor
        }
    }

    /**
    * Validar o acesso à aplicação utilizando as credenciais (email e password)
    */
    public void iniciaSessao (String email , String password ) throws SemAutorizacaoException{

        if(!users.containsKey(email)){
            throw new SemAutorizacaoException("O email não está registado.");
        }
        else if(!users.get(email).getPassword.equals(password)){
            throw new SemAutorizacaoException("A password está errada.");
        }
        else{
            logado=true;
            userAtual=users.get(email); //Ver melhor
        }
    }

    /**
    * Fechar a sessão do utilizador
    */
    public void fechaSessao (){

        logado=false;
        userAtual=null;
    }

    /**
    * Consultar a lista de todos os imóveis de um dado tipo (Terreno, Moradia, etc.) e até um certo preço.
    */
    public List<Imovel> getImovel(String classe, int preco){
        return null; //TODO
    }

    /**
    * Consultar a lista de todos os imóveis habitáveis (até um certo preço).
    */

   /*public List<Habitavel> getHabitaveis(int preco){
        return null;
    } */

    /**
    * Obter um mapeamento entre todos os imóveis e respectivos vendedores.
    */
    public Map<Imovel, Vendedor> getMapeamentoImoveis(){
        return null;
    }

    /**
    * Verifica dado um id se esse imóvel existe
    **/
    public boolean existeImovel(String idImovel){
        if(imoveis.containsKey(idImovel) return true;
        else return false;
    }

    /**
    * Marcar um imóvel como favorito.
    **/
    public void setFavorito(String idImovel) SemAutorizacaoException {

           if(logado==false){ //Falta diferenciar comprador de vendedor
                throw new SemAutorizacaoException("Não tem permissões para marcar um imóvel como favorito.");
           }
           else{
                    if(existeImovel(idImovel) == false){
                         throw new ImovelInexistenteException("O imóvel não existe");
                    }
                  else{
                        favoritos.clear();
                        for(Imovel i: favoritos){
                        favoritos.add(i.clone());
                        }
                  }
          }
    }

    /**
    * Consultar imóveis favoritos ordenados por preço
    **/
    public TreeSet<Imovel> getFavoritos() throws SemAutorizacaoException{
        return null;
    }

    /**
    * Colocar um imóvel à venda;
    */
    public void registaImovel(Imovel im) throws ImovelExisteException , SemAutorizacaoException{
    }

    /**
    * Visualizar uma lista com as datas (e emails, caso exista essa informação) das
    * 10 últimas consultas aos imóveis que tem para venda
    */
    public List<Consulta> getConsultas() throws SemAutorizacaoException{
        return null;
    }

    /**
    * Alterar o estado de um imóvel, de acordo com as acções feitas sobre ele
    */
    public void setEstado(String idImovel , String estado) throws ImovelInexistenteException , SemAutorizacaoException , EstadoInvalidoException{

    }

    /**
    * Obter um conjunto com os códigos dos imóveis
    * mais consultados (ou seja, com mais de N consultas)
    */
    public Set<String> getTopImoveis(int n){
        return null;
    }


}