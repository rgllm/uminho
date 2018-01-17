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
public class Memory {
    
    double total_bytes;
    double free_bytes;
    double percentage_free_bytes;
    
    public Memory(){
        this.total_bytes = 0.0;
        this.free_bytes = 0.0;
        this.percentage_free_bytes = 0.0;
    }
    public Memory(double tb,double fb, double pfb){
        this.total_bytes = tb;
        this.free_bytes = fb;
        this.percentage_free_bytes = pfb;
    }
    public double getTb(){ return this.total_bytes;}
    public double getFb(){ return this.free_bytes;}
    public double getPfb(){ return this.percentage_free_bytes;}
    
}
