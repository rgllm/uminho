/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import jade.core.Agent;

public class ClientAgent extends Agent {

	protected void setup(){
		float x = (float) (Math.random() * 100);
		float y = (float) (Math.random() * 100);
		ClientBehav c = new ClientBehav(this, 1000,20,x,y);
		addBehaviour(c);
	}

	protected void takeDown(){
		super.takeDown();
	}

}





