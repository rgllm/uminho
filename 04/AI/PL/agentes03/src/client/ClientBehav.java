/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

public class ClientBehav extends TickerBehaviour{

	float[] pos = new float[2];
	float time;
	
	public ClientBehav(Agent a, long period,long time,float x, float y) {
		super(a, period);
		pos[0]=x;
		pos[1]=y;
		this.time= time;
		// TODO Auto-generated constructor stub
	}
	
	public ClientBehav(Agent a, long period) {
		super(a, period);
		// TODO Auto-generated constructor stub
	}

	protected void onTick() {
		time --;
		
		ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
		msg.addReceiver(new AID("Manager", AID.ISLOCALNAME));
		
		if(time <= 0){
			msg.setContent("END");
			myAgent.send(msg);
			stop();
		}
		else{
			msg.setContent(pos[0]+" "+pos[1]);
			myAgent.send(msg);
		}
	}
}

