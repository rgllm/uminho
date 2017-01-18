
import java.util.ArrayList;
import java.util.HashMap;


class Sistema{
    private ArrayList<User> users;
    private ArrayList<Leilao> leiloes;
    private int lastUserId;
    private int lastLeilaoId;
    HashMap<Integer,ArrayList<String>> notifications; // a cada userId corresponde uma lista de notificações

    public Sistema(){
        users= new ArrayList<>();
        leiloes= new ArrayList<>();
        this.lastUserId=0;
        this.lastLeilaoId=0;
        notifications= new HashMap<>();
    }

    public boolean hasNotifications(int userId){
        return notifications.get(userId).size()>0;
    }

    synchronized public int addUser(String username, String password, int tipo){
        if(isRegistered(username))
            return -1;
        lastUserId++;
        users.add(new User(lastUserId,username, password, tipo));
        return lastUserId;
    }

    synchronized public int addLeilao(int sellerId, float base_price,String item_description){
        lastLeilaoId++;
        leiloes.add(new Leilao(lastLeilaoId, sellerId, base_price,item_description));
        return lastLeilaoId;
    }

    public boolean isRegistered(String username){
        for(User x : users)
            if(x.username.equals(username))
                return true;
        return false;
    }

    synchronized public int login(String username,String password){
        for(User x : users)
            if(x.username.equals(username) && x.password.equals(password))
                return x.id;
        return -1;
    }

    public ArrayList<String> getLeiloes(int userId){
        if(getTipoUser(userId)==1)
            return getLeiloesBuyer(userId);
        else if(getTipoUser(userId)==2)
            return getLeiloesSeller(userId);
        else return null;
    }

    public ArrayList<String> getLeiloesBuyer(int id_Buyer){
        ArrayList<String> ret = new ArrayList<>();
        for(Leilao l : leiloes){
            if(l.id_highestLic==id_Buyer)
                ret.add("Id do Leilão: "+ l.id  + " , Descrição : " + l.item_description + " , Preço base: " + l.base_price + " , Licitação mais alta: "+l.highestLic()+ " +");
            else ret.add("Id do Leilão: "+ l.id  + " , Descrição : " + l.item_description + " , Preço base: " + l.base_price + " , Licitação mais alta: "+l.highestLic());
        }
        return ret;
    }

    public ArrayList<String> getLeiloesSeller(int id_Seller){
        ArrayList<String> ret = new ArrayList<>();
        for(Leilao l : leiloes){
            if(l.sellerId==id_Seller)
                ret.add("Id do Leilão: "+ l.id  + " , Descrição : " + l.item_description + " , Preço base: " + l.base_price + " , Licitação mais alta: "+l.highestLic() + " *");
            else ret.add("Id do Leilão: "+ l.id  + " , Descrição : " + l.item_description + " , Preço base: " + l.base_price + " , Licitação mais alta: "+l.highestLic());
        }
        return ret;
    }

    public User getUser(int id){
        for(User u : users)
            if(u.id==id)
                return u;
        return null;
    }

    public int getTipoUser(int userId){
        for(User u : users)
            if(u.id==userId)
                return u.tipo;
        return -1;
    }

    synchronized public int licita(int userId, int leilaoId , float value){
        for(Leilao l : leiloes )
            if (l.id==leilaoId){
                return l.licitar(userId,value);
            }
        return 0;
    }

    synchronized public DuploIntFloat terminaLeilao(int userId, int leilaoId){
        DuploIntFloat ret=null;
        Leilao aux=null;
        for(Leilao l : leiloes)
            if(l.id==leilaoId && l.sellerId==userId){
                ArrayList<Integer> ids= new ArrayList<>();
                ids.add(userId);
                for(int id : l.licitations.keySet()){
                    if(id!=0) ids.add(id);
                }
                ret= new DuploIntFloat(l.id_highestLic, l.highestLic(), ids);
                aux=l;
                break;
            }
                // comprador ganhador
                // valor de fecho
        if(ret!=null)
            leiloes.remove(aux);
        return ret;
    }
}