/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern;

import java.util.ArrayList;

/**
 *
 * @author rgllm
 */
public class Person implements Observer {
    String name;
    
    public Person(String nome){
        this.name=name;
    }
    
    public void checkMail(ArrayList<Mail> m){
        if(m.size()!=0){
        for(Mail r: m){
            if(name.compareTo(r.receiverName) == 0){
                System.out.println(name + ": " + r.content);
            }
        }
        }
    }
    
    public void update(Object obj){
        
        if(obj instanceof PostOffice){
            PostOffice po = (PostOffice) obj;
            checkMail(po.getState());
        }
        
    }
}
