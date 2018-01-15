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
public class PostOffice implements Subject {
    
    private ArrayList<Mail> allMail;
    private ArrayList<Observer> observers;
    
    public PostOffice(){
        allMail = new ArrayList<>();
        observers = new ArrayList<>();
    }
    
    public void addMail(Mail m){
        allMail.add(m);
        Notify();
    }
    
    public ArrayList<Mail> getState(){
        return allMail;
    }
    
    public void Attach(Observer o){
        observers.add(o);
    }
    
    public void Dettach(Observer o){
        observers.remove(o);
    }
    
    public void Notify(){
        for(Observer o: observers){
            o.update(this);
        }
        
    }
    
    
}
