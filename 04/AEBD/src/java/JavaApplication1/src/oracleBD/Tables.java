/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracleBD;

/**
 *
 * @author zmlef
 */
public class Tables {
   
   String owner;
   int owner_id;
   String table_name;
   String tablespace_name;
   int number_of_access;
   String erased;
   int number_of_regists;
   
   public Tables(){
       this.owner = null;
       this.owner_id = 0;
       this.tablespace_name = null;
       this.table_name = null;
       this.number_of_access = 0;
       this.erased = null;
       this.number_of_regists = 0;
   }
   public Tables(String ow, String tspn, String tn, String era,int numr)
    {
       this.owner = ow;
       this.tablespace_name = tspn;
       this.table_name = tn;
       this.erased = era;
       this.number_of_regists = numr;
   }
   public String getName(){ return this.table_name; }
   public int getOwner_id(){return this.owner_id;}
   public String getOwner(){return this.owner;}
   public String getTablespace(){ return this.tablespace_name;}
   public int getNA(){ return this.number_of_access; }
   public String getER(){ return this.erased; }
   public int getNR(){ return this.number_of_regists; }
   public void setNA(int na){ this.number_of_access = na;}
   public void setOwner(String ow){ this.owner = ow;}
   public void setOwner_id(int id){ this.owner_id = id;}
   
    
}
