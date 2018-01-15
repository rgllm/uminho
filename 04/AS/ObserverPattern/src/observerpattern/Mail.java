/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package observerpattern;

/**
 *
 * @author rgllm
 */
public class Mail {
    String receiverName;
    String address;
    String content;
    
    public Mail(String name, String address, String content){
        this.receiverName=name;
        this.address=address;
        this.content=content;
    }
    
}
