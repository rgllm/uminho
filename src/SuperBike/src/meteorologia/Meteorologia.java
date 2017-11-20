/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package meteorologia;
import jess.*;

/**
 *
 * @author rgllm
 */
public class Meteorologia {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
                Rete engine = new Rete();
                engine.batch("meteorologia/meteo.clp");
                engine.reset();
                engine.run();

        } catch (JessException e) {
            System.err.println(e);
        }
    }
    
}