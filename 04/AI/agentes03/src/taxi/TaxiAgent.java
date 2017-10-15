/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxi;

import jade.core.Agent;

public class TaxiAgent extends Agent {

	protected void setup(){
		TaxiBehav t = new TaxiBehav();
		addBehaviour(t);
	}
	
	protected void takeDown(){
		super.takeDown();
	}
}
