package decider;
import java.util.ArrayList;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jess.JessException;
import jess.Rete;
import stacion.Stacion;

@SuppressWarnings("serial")
public class JessInfo extends CyclicBehaviour {
	private Rete engine;
	Node nodos;
	private ArrayList<Stacion> stacions = new ArrayList<Stacion>();
	private String weather;
	
    public JessInfo(Agent a,String filename){
        engine = new Rete();
        Writer w = new Writer();
        nodos = w.populate();
        
        try {
        	engine.batch(filename);
            engine.reset();
        } catch (JessException ex) {
            ex.printStackTrace();
        }
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
	        for(i=0;i!=splited.length;i++) {
	        	stacions.add(new Stacion(splited[i]));
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
			if(msg.getContent().charAt(0)=='W')
				weather = msg.getContent();
		}
    }
    
    
	@Override
	public void action() {
       ACLMessage msg = myAgent.receive();
       if(msg != null){
    	   if(msg.getContent().length() > 0) {
	    	   String[] res = msg.getContent().split("\\s+");
	           updateStacions("Go",Float.parseFloat(res[7]),Float.parseFloat(res[8]));
	           getWeather();
	    	   try {
	                  ACLMsg2Jess(msg);
	                  engine.run();  
	                  System.out.println(engine.fetch(res[0]));
	            } catch (JessException ex) {
	                ex.printStackTrace();
	            }
    	   }   	
       }
    }
    
   /*
    * Asserts a Fact that represents the msg in Jess
   */
   public boolean addFact(String fact){
        try {
            engine.executeCommand(fact);
        } catch (JessException ex) {
            return false;
        }
       return true;
   }
   	
	/*
     * Convert a ACLMessage to a JESS Fact  
     */
     public boolean ACLMsg2Jess(ACLMessage msg){
    	 StringBuilder sb = new StringBuilder();
         sb.append("(assert (ACLMessage ");
         sb.append("(communicative-act ").append(msg.getPerformative()).append(")");

         if(msg.getSender() != null){
         	sb.append(" (sender ").append(msg.getSender().getName()).append(")");
         }
          
         if(msg.getContent()!=null){
         	sb.append("  (content ").append(msg.getContent()).append(")");        
         }

         sb.append("))");
         String jmsg = sb.toString();
         return addFact(jmsg);
     }
}
     