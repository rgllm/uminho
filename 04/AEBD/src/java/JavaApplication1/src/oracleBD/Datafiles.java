/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracleBD;

public class Datafiles {
    
  int file_id;
  Tablespaces t;
  String name;
  int free_space;
  int size;
  int used_space;
  float percentage_used;
  
  public Datafiles(){
      this.file_id = 0;
      t = new Tablespaces();
      this.name = null;
      this.free_space = 0;
      this.size = 0;
      this.used_space = 0;
      this.percentage_used = 0;
  }
  public Datafiles(int f_id, Tablespaces table,String n , int fs ,int size, int us, float pu){
      this.file_id = f_id;
      this.t = table;
      this.name = n;
      this.free_space = fs;
      this.size = size;
      this.percentage_used = pu;
      this.used_space = us;
  }
  public int getFile_id(){return this.file_id;}
  public Tablespaces getTablespace(){ return this.t;}
  public String getName(){return this.name;}
  public int getFree_space(){return this.free_space;}
  public int getUsed_space(){return this.used_space;}
  public float getPercentage_used(){return this.percentage_used;}
  public int getSize(){return this.size;}
   
}
