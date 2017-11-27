/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distance;

import jade.core.Agent;

public class DistanceAgent extends Agent {
	
	private final int taxiNumber =5;
	
	protected void setup(){
		DistanceBehav d = new DistanceBehav(taxiNumber);
		addBehaviour(d);
	}
	
	protected void takeDown(){
		super.takeDown();
	}
}

