package Agents;

import jade.core.AID;
import jade.core.Agent;
import jade.lang.acl.ACLMessage;

public class librarian extends Agent {

	@Override 
	protected void setup(){
		
	  System.out.println("Librarian: "+getAID().getName()); 
		
	  //Send a list of available books
	  
	  	ACLMessage books = new ACLMessage(ACLMessage.INFORM);
		 books.addReceiver(new AID("costumer", AID.ISLOCALNAME));
		 books.setContent("Available Books");
		 send(books);
	
	    //Receive a requesting from the costumer
	      
	      
	     
	      
	    //Send a confirmation if the book exists in the catalogue
	      
        ACLMessage msg = null;
        msg = blockingReceive();
        System.out.println(msg.getContent());
      } 
    
    @Override
    protected void takeDown() {
        // TODO Auto-generated method stub
        super.takeDown();
    }
   

}
