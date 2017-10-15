/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import jade.core.Agent;
import jade.core.behaviours.ParallelBehaviour;

public class Manager extends Agent {
	protected void setup(){
		ParallelBehaviour p = new ParallelBehaviour(this,ParallelBehaviour.WHEN_ALL);
		addBehaviour(p);
		p.addSubBehaviour(new ManagerBehav("taxi 0"));
		p.addSubBehaviour(new ManagerBehav("taxi 1"));
		p.addSubBehaviour(new ManagerBehav("taxi 2"));
		p.addSubBehaviour(new ManagerBehav("taxi 3"));
		p.addSubBehaviour(new ManagerBehav("taxi 4"));
		
	}
	
	protected void takeDown(){
		super.takeDown();
	}
}
