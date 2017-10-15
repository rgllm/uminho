/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package taxi;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class TaxiBehav extends CyclicBehaviour {

	float[] pos = new float[2];
	float distance = 0;
	int calls = 0;
	
	public void setup(){
		pos[0] = (float) (Math.random() * 100);
		pos[1] = (float) (Math.random() * 100);
	}
	
	public void action() {
		setup();
		ACLMessage msg = myAgent.receive();
		if(msg != null){
			//propose to go
			if (msg.getPerformative() == 11) {
				ACLMessage inf = new ACLMessage(ACLMessage.PROPOSE);
				inf.addReceiver(msg.getSender());
				inf.setContent("pos: "+pos[0]+" "+pos[1]);
				myAgent.send(inf);
	 			
			}
			//inform render info
			else if (msg.getPerformative() == 7 && msg.getContent().charAt(0)=='r') {
				System.out.println(myAgent.getName()+":\ndist: "+distance+";\ncalls: "+calls);
				
	 			
			}
			//inform
			else if(msg.getPerformative() == 7){
				System.out.println(msg.getContent());
				calls++;
				distance += Float.parseFloat(msg.getContent());
			}
		}
	}

}
