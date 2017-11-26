package stacion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class HeadBehav extends CyclicBehaviour {
	private static HashMap<String,Stacion> stacions = new HashMap<String,Stacion>();
	
	
	public HashMap<String,Stacion> toStacion(ArrayList<String> s) {
		HashMap<String,Stacion> sta = new HashMap<String, Stacion>();
		Stacion tmp;
		for(String st : s) {
			tmp = new Stacion(st);
            sta.put(tmp.getName(),tmp);
        }
		return sta;
	}
	
	public void populate() {
		StationWriter s = new StationWriter();
		stacions = toStacion(s.readFile());
	}
	
	public Boolean notVeryFarGo(Float x,Float y,Stacion s) {
		Boolean notFar = false;
		if( Math.sqrt(Math.pow((x-s.getX()),2) + Math.pow(y-s.getY(),2)) < s.getAreaGo()) {
			notFar = true;
		}
		return notFar;
	}
	
	public Boolean notVeryFarLeave(Float x,Float y,Stacion s) {
		Boolean notFar = false;
		if( Math.sqrt(Math.pow((x-s.getX()),2) + Math.pow(y-s.getY(),2)) < s.getAreaLeave()) {
			notFar = true;
		}
		return notFar;
	}
	
	
	@Override
    public void action() {
		populate();
		ACLMessage msg= myAgent.receive();
        if(msg!=null){
        	String[] parts = msg.getContent().split("\\s+");
            if(parts[0].equals("Listar")){
                ACLMessage todasEstacoes = new ACLMessage( ACLMessage.INFORM );
                StringBuilder answer = new StringBuilder();
                for(Entry<String, Stacion> entry : stacions.entrySet()) {
    				answer.append(entry.getKey()).append('\n');
    			}
                todasEstacoes.setContent(answer.toString());
                todasEstacoes.addReceiver(msg.getSender());
                myAgent.send(todasEstacoes);
            }
            else if(parts[0].equals("+")) {
            	if(stacions.get(parts[1])!=null) {
            		if(stacions.get(parts[1]).addBike()) {
            			ACLMessage inf = new ACLMessage( ACLMessage.INFORM );
                        inf.setContent("Bicicleta deixada em" + parts[1]);
                        inf.addReceiver(msg.getSender());
                        myAgent.send(inf);
            		}
            		else {
	            		ACLMessage inf = new ACLMessage( ACLMessage.INFORM );
	                    inf.setContent("Não foi possivel deixar a bicicleta em" + parts[1]);
	                    inf.addReceiver(msg.getSender());
	                    myAgent.send(inf);
            		}
            	}
            }
            else if(parts[0].equals("-")) {
            	if(stacions.get(parts[1])!=null) {
            		if(stacions.get(parts[1]).remBike()) {
            			ACLMessage inf = new ACLMessage( ACLMessage.INFORM );
                        inf.setContent("Bicicleta levantada em" + parts[1]);
                        inf.addReceiver(msg.getSender());
                        myAgent.send(inf);
            		}
            		else {
	            		ACLMessage inf = new ACLMessage( ACLMessage.INFORM );
	                    inf.setContent("Não foi possivel deixar a bicicleta em" + parts[1]);
	                    inf.addReceiver(msg.getSender());
	                    myAgent.send(inf);
                    }
            	}
            }
            else if(parts[0].equals("Pos:")) {
            	ACLMessage todasEstacoes = new ACLMessage( ACLMessage.INFORM );
                StringBuilder answer = new StringBuilder();
                answer.append("S \n");
                for(Entry<String, Stacion> entry : stacions.entrySet()) {
                	if(parts[3].charAt(0) == 'G') {
	    				if(notVeryFarGo(Float.parseFloat(parts[1]),Float.parseFloat(parts[2]), entry.getValue())) {
	    					answer.append(entry.getValue().toString()).append('\n');
	    				}
                	}
                	else if(parts[3].charAt(0) == 'L') {
	    				if(notVeryFarLeave(Float.parseFloat(parts[1]),Float.parseFloat(parts[2]), entry.getValue())) {
	    					answer.append(entry.getValue().toString()).append('\n');
	    				}
                	}
    			}
                todasEstacoes.setContent(answer.toString());
                System.out.println("stacion:" + answer.toString());
                todasEstacoes.addReceiver(msg.getSender());
                myAgent.send(todasEstacoes);
            }
        }  
    block();   
    }

}
