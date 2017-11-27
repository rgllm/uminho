/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package distance;


import java.util.Map.Entry;
import java.util.TreeMap;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class DistanceBehav extends CyclicBehaviour {

	TreeMap<Integer,TreeMap<String,Float>> map;
	int taxi;
	
	
	public DistanceBehav(int taxiNumber){
		taxi = taxiNumber;
		map = new TreeMap<Integer,TreeMap<String,Float>>();
	}
	
	
	public void action() {
		String taxiGo = null;
		ACLMessage msg = myAgent.receive();
		if (msg != null) {
			String[] splited = msg.getContent().split("\\s+");
			
			if(map.size()<=Integer.parseInt(splited[0])){
				map.put( Integer.parseInt(splited[0]), new TreeMap<String,Float>());
			}
			
			map.get(Integer.parseInt(splited[0])).put(splited[2],Float.parseFloat(splited[1]));
			if(map.get(Integer.parseInt(splited[0])).size() >= taxi){
				taxiGo = findMin(Integer.parseInt(splited[0]));
				ACLMessage msg2 = new ACLMessage(ACLMessage.INFORM);
				msg2.addReceiver(new AID(taxiGo, AID.ISLOCALNAME));
				msg2.setContent(String.valueOf(map.get(Integer.parseInt(splited[0])).get(taxiGo)));
				myAgent.send(msg2);
			}
		}
	}
	
	
	public String findMin(int n){
		float min= -1;
		String taxi = null;
		for(Entry<String, Float> entry : map.get(n).entrySet()) {
			String key = entry.getKey();
			Float value = entry.getValue();
			if(value<min || min == -1){
				min=value; 
				taxi=key;
			}	  
		}
		return taxi;
	}
}
