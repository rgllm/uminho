/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rgllm
 */
public class Venda Serializable {

    private String venda;

    public Venda(String v){venda=v;}
    public Venda(Venda v){venda=v.getVenda();}

    public String getVenda(){return venda;}

    public Venda clone(){
       return new Venda(this);
    }

    public toString(){
       return venda;     
    }
    
    public boolean equals(Object o){
        if(o==null) return false;
        if (o==this) return true;
        if (this.getClass()!=o.getClass()) return false;
        Venda obj = (Venda) o;
        if (venda.equals(o.getVenda())) return false;
        return true;
    }


}
