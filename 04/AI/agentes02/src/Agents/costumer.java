package Agents;

import jade.core.Agent;
import jade.core.AID;
import jade.lang.acl.ACLMessage;

public class costumer extends Agent {
	
	@Override 
	protected void setup(){
		
		 System.out.println("Costumer: "+getAID().getName()); 
			
		 //Receives the list of available books from the librarian
		
		
		//Send a confirmation for one book
		
		
		//Receives the confirmation from the librarian
		
	      ACLMessage message = new ACLMessage(ACLMessage.INFORM);
	      message.addReceiver(new AID("librarian", AID.ISLOCALNAME));
	      message.setContent("Hello The World");
	      send(message);
	  }
    
    @Override
    protected void takeDown() {
        // TODO Auto-generated method stub
        super.takeDown();
    }
	

}
