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
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        PostOffice po = new PostOffice();
        Person chris = new Person("chris");
        Person john = new Person("john");
        Mail m = new Mail("John","Braga","Hello, how are you?");
        
        po.Attach(chris);
        po.Attach(john);
        
        po.addMail(m);
    }
    
}
