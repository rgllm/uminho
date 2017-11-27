/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes06;

import jess.*;

public class Agentes06 {

    public static void main(String[] args) {
        try {
                Rete engine = new Rete();
                engine.batch("agentes06/ex3.clp");
                engine.reset();
                engine.run();

        } catch (JessException e) {
            System.err.println(e);
        }
    }
    
}
