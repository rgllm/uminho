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
public class IOWrites {
    String name;
    String begin_time;
    String end_time;
    double value; /* per sec */
    
    public IOWrites(){
        this.name = null;
        this.begin_time = null;
        this.end_time = null;
        this.value = 0.0;
    }
    public IOWrites(String n, String b, String e,double v){
        this.name = n;
        this.begin_time = b;
        this.end_time = e;
        this.value = v;
    }
    public String getName(){return this.name;}
    public String getBt(){return this.begin_time;}
    public String getEt(){return this.end_time;}
    public double getValue(){return this.value;}
}
