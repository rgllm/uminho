/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agents;

import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Tiago
 */
public class Mother extends Agent {
   private String deciderID;
   private ContainerController container;
   private int i;
   
   //recebe o id do Decider e o Container a partir da Main
   Mother(String deciderID, ContainerController c) {
       this.deciderID = deciderID;
       this.container = c;
   }
   
   @Override
   public void setup() {
       i=1;
       this.addBehaviour(new createAgent(this,1000));
   }
   
   private class createAgent extends TickerBehaviour {
       
       public createAgent(Agent t, long time) {
           super(t,time);
       }
       
       @Override
       protected void onTick() {
           Utilizador utilizador = new Utilizador(deciderID);
           i++;
           try {    
               container.acceptNewAgent("Utilizador" + i, utilizador).start();
           } 
           catch (StaleProxyException ex) {
               Logger.getLogger(Mother.class.getName()).log(Level.SEVERE, null, ex);
           }
           catch (NullPointerException ex) {
               Logger.getLogger(Mother.class.getName()).log(Level.SEVERE, null, ex);
           }
        }
    }
   
    @Override
    public void takeDown() {
        super.takeDown();
    }
    
}
