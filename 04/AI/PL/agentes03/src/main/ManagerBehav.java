/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class ManagerBehav extends CyclicBehaviour {

	private String agentName;
	private int count = 0;
	
	public ManagerBehav(String name){
		agentName = name;
	}
	
	
	public void action() {
		float dist;
		float cliDist = 0;
		ACLMessage msg = myAgent.receive();
		
		if (msg != null) {
			ACLMessage inf = new ACLMessage(ACLMessage.PROPOSE);
			
			if(msg.getPerformative() == 7 && msg.getContent().charAt(0)=='e'){
				ACLMessage end = new ACLMessage(ACLMessage.INFORM);
				end.addReceiver(new AID(agentName, AID.ISLOCALNAME));
				end.setContent("render the results");
				myAgent.send(end);
			}
			else if(msg.getPerformative() == 7){
				inf.addReceiver(new AID(agentName, AID.ISLOCALNAME));
				inf.setContent("please tell me where you are");
				String[] splited = msg.getContent().split("\\s+");
				cliDist = (float) Math.sqrt(Math.pow(Float.parseFloat(splited[0]),2)+Math.pow(Float.parseFloat(splited[1]),2));
				myAgent.send(inf);
			}
			else if(msg.getPerformative() == 11){
				String[] splited = msg.getContent().split("\\s+");
				dist = (float) Math.sqrt(Math.pow(Float.parseFloat(splited[1]),2)+Math.pow(Float.parseFloat(splited[2]),2));
				dist = dist - cliDist;
				inf.addReceiver(new AID("Distance", AID.ISLOCALNAME));
				inf.setContent(count+" "+Float.toString(dist)+" "+agentName);
				count++;
				myAgent.send(inf);		
			}
		}
	}
}
