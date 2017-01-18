
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

class DuploIntFloat{
    int idVencedor;
    float valorFecho;
    ArrayList<Integer> ids;
    
    public DuploIntFloat(int v1, float v2,ArrayList<Integer> ids){
        this.idVencedor=v1;
        this.valorFecho=v2;
        this.ids=ids;
    }
    public DuploIntFloat(){
        this.idVencedor=-1;
        this.valorFecho=-1;
    }
    
}
