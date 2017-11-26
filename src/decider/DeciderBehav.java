package decider;
import java.util.HashMap;
import java.util.Map.Entry;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import stacion.Stacion;

@SuppressWarnings("serial")
public class DeciderBehav extends CyclicBehaviour {
	private HashMap<String,HashMap<String,Pair>> words;
	private HashMap<String,Stacion> stacions = new HashMap<String,Stacion>();
	private String weather = "QUENTE"; //estado inicial para evitar valores nulos
	private float[] weigth = new float[6];
	
    public DeciderBehav(){
        Writer w = new Writer();
        words = w.populate();
        totalWeigth();
    }
    
    
    public float calc(HashMap<String,Pair> n) {
    	int accept = 0;
    	int reject = 0;
    	
    	for(Entry<String, Pair> entry : n.entrySet()) {
    		accept += entry.getValue().getAccept();
    		reject += entry.getValue().getReject();
		}
    	
    	return accept/reject;
    }
    
    public void totalWeigth() {
    	weigth[0]= calc(words.get("spirit"));
    	weigth[1]= calc(words.get("age"));
    	weigth[2]= calc(words.get("sex"));
    	weigth[3]= calc(words.get("healt"));
    	weigth[4]= calc(words.get("phisic"));
    	weigth[5]= calc(words.get("weather"));
    }
    
    
    public void updateStacions(String go,Float x, Float y) {
    	ACLMessage inf = new ACLMessage( ACLMessage.INFORM );
        inf.setContent("Pos: " + x + " " + y + " " + go );
        inf.addReceiver(new AID("StacionHead", AID.ISLOCALNAME));
        myAgent.send(inf);
        
        try {
	        ACLMessage msg =myAgent.receive();
	        String[] splited = msg.getContent().split("\n");
	        int i;
	        for(i=1;i!=splited.length;i++) {
	        	Stacion tmp = new Stacion(splited[i]);
	        	if(!stacions.containsKey(tmp.getName())) {
	        		stacions.put(tmp.getName(),tmp);
	        	}
	        	
	        }
        }catch (Exception e) {}
    }
    
    public void getWeather() {
    	
	    	ACLMessage inf = new ACLMessage(ACLMessage.INFORM);
			inf.addReceiver(new AID("Meteo", AID.ISLOCALNAME));
			inf.setContent("M");
			myAgent.send(inf);
			
			ACLMessage msg =myAgent.receive();
		if(msg!=null) {
			if(msg.getContent().charAt(0)=='W') {
				String[] splited = msg.getContent().split("\\s+");
				weather = splited[1];
			}
		}
    }
    
    
    public Boolean check(String s, String stacion) {
    	Boolean checked = false;
    	ACLMessage inf = new ACLMessage(ACLMessage.INFORM);
		inf.addReceiver(new AID("StacionHead", AID.ISLOCALNAME));
		if(s.contentEquals("Go")) {
			inf.setContent("-" + " " + stacion);
		}
		else if(s.contentEquals("Leave")) {
			inf.setContent("+" + " " + stacion);
		}
		
		myAgent.send(inf);
		
		ACLMessage msg =myAgent.receive();
		if(msg!=null) {
			if(msg.getContent().charAt(0)=='N') {
				checked = false;
			}
			else if(msg.getContent().charAt(0)=='B') {
				checked = true;
			}
		}
    	return checked;
    }
    
	@Override
	public void action() {
		int i;
		ACLMessage msg = myAgent.receive();
		Boolean notserved = true;
		if(msg != null){
			if(msg.getContent().charAt(0)=='A') {
				String[] res = msg.getContent().split("\\s+");
	    	   	getWeather();
	    		
	    	   	
	    	   	updateStacions("Go",Float.parseFloat(res[7]),Float.parseFloat(res[8]));
	    	   	Response go = new Response(false,"not set");
	    	   	while(notserved) {
	    	   		go = decide(msg.getContent(),"Go");
	    	   		for(i=0;i!=5;i++) {
	    	   			if(go.getStacion().contentEquals("Não há estações disponiveis")) {
	    	   				go = decide(msg.getContent(),"Go");
	    	   			}
	    	   			else break;
	    	   		}
	    	   	
	    	   		notserved = check("Go",go.getStacion());
	    	   		stacions.remove(go.getStacion());
	    	   	}
	    	   	updateStacions("Leave",Float.parseFloat(res[7]),Float.parseFloat(res[8]));
	    	   	Response leave = new Response(false,"not set");;
	    	   	notserved = true;
	    	   	while(notserved) {
	    	   		leave = decide(msg.getContent(),"Leave");
	    	   		for(i=0;i!=5;i++) {
	    	   			if(leave.getStacion().contentEquals("Não há estações disponiveis")) {
	    	   				leave = decide(msg.getContent(),"Leave");
	    	   			}
	    	   			else break;
	    	   		}
	    	   		notserved = check("Leave",leave.getStacion());
	    	   		if(!notserved) {
	    	   			stacions.remove(leave.getStacion());
	    	   		}
	    	   	}
	    	   	
	    	   	go.print(res[1],"Go");
	    	   	leave.print(res[1],"Leave");
    	   }   	
       }
    }


	private Response decide(String user, String state) {
		Response r = null;
		String[] splited = user.split("\\s+");
		float res = Integer.parseInt(splited[2]) * 1/weigth[0];
		res += 10/Integer.parseInt(splited[3]) * 1/weigth[1];
		res += Integer.parseInt(splited[4]) * 1/weigth[2];
		res += Integer.parseInt(splited[5]) * 1/weigth[3];
		res += Integer.parseInt(splited[6]) * 1/weigth[4];
		
		switch(weather) {
		case "TROVOADA": 
			res += 0 * 1/weigth[5];
			break;
		case "CHUVA": 
			res += 1 * 1/weigth[5];
			break;
		case "FRIO":
			res += 2 * 1/weigth[5];
			break;
		case "NEVOEIRO":
			res += 3 * 1/weigth[5];
			break;
		case "QUENTE":
			res += 4 * 1/weigth[5];
			break;
		
		}
		
		String minCostSt = null;
		String minDistSt = null;
		Float minDist = -1f;
		Float minCost = -1f;
		Float x = 0f;
		Float y = 0f;
		
		if(state.contentEquals("Go")) {
			x = Float.parseFloat(splited[7]);
			y = Float.parseFloat(splited[8]);
		}
		else if(state.contentEquals("Leave")) {
			x = Float.parseFloat(splited[9]);
			y = Float.parseFloat(splited[10]);
		}
	
		for(Entry<String,Stacion> en : stacions.entrySet()) {
			if(minDist == -1 || minDist > dist(x,y,en.getValue())) {
				minDist = dist(x,y,en.getValue());
				minDistSt = en.getKey();
			}
			if(minCost == -1 || minCost > en.getValue().getPrice(state)) {
				minCost = en.getValue().getPrice(state);
				minCostSt = en.getKey();
			}
		}
		if(minDistSt != null) {
			if(minDistSt.contentEquals(minCostSt)){
				r = new Response(false,minDistSt);
			}
			else if(res < 10) {
				//Não Aceitou o desconto
				r = new Response (false,minDistSt);
			}
			else if(res >= 10) {
				//Não Aceitou o desconto
				r = new Response (false,minCostSt);
			}
		}
		else r = new Response(false,"Não há estações disponiveis");
		
		return r;
	}
	
	
	public float dist(Float x,Float y, Stacion s) {
		return (float) Math.sqrt(Math.pow((x-s.getX()),2) + Math.pow(y-s.getY(),2));
	}
}
     