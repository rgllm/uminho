
import java.util.HashMap;
import java.util.Map;



class Leilao{
    int id;
    int sellerId; // id do vendedor que cria o leilao
    int id_highestLic; // id do comprador que detem a maior licitação
    float base_price;
    String item_description;
    Map<Integer,Float> licitations;  // INTEGER -> id do user ; Float -> valor da licitação

    public Leilao(int id, int sellerId, float base_price,String item_description){
        this.id=id;
        this.sellerId=sellerId;
        id_highestLic=0;
        this.base_price=base_price;
        this.item_description=item_description;
        licitations= new HashMap<>();
        licitations.put(0,(float)0.0);
    }

    synchronized public int licitar(int userId, float amount){
        if(amount < this.base_price) return -1;
        if(amount <= this.highestLic()) return -2;
        id_highestLic= userId;
        licitations.put(userId,amount);
        return this.id;
    }
// highestLic() = valor da licitaçao mais alta
    synchronized public float highestLic(){
        return licitations.get(id_highestLic);
    }

}