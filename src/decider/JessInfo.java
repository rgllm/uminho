package decider;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jess.JessException;
import jess.Rete;

@SuppressWarnings("serial")
public class JessInfo extends CyclicBehaviour {
	private Rete engine;
	Node nodos;
	
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
    
	@Override
	public void action() {
       ACLMessage msg = myAgent.receive();
       
       if(msg != null){
            try {
                  ACLMsg2Jess(msg);
                  engine.run();  
                  System.out.println(engine.fetch("word"));
            } catch (JessException ex) {
                ex.printStackTrace();
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
     