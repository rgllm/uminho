/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracleBD;

public class Tablespaces {
    
  String name;
  int free_space;
  int used_space;
  float percentage_used;
  int total_used;
  
  public Tablespaces(){
      this.name = null;
      this.free_space = 0;
      this.used_space = 0;
      this.percentage_used = 0;
      this.total_used = 0;
  }
  public Tablespaces(String n , int fs , int us, float pu,int tu){
      this.name = n;
      this.free_space = fs;
      this.percentage_used = pu;
      this.used_space = us;
      this.total_used = tu;
  }
  public String getName(){return this.name;}
  public int getFree_space(){return this.free_space;}
  public int getUsed_space(){return this.used_space;}
  public float getPercentage_used(){return this.percentage_used;}
  public int getTotal_used(){return this.total_used;}
   
}
