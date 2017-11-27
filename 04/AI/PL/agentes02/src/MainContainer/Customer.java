package MainContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.SimpleBehaviour;
import jade.lang.acl.ACLMessage;

public class Customer extends Agent {
	
	private List<String> books = new ArrayList<>();
	private static Random rand = new Random();
	private String senderID = "Customer " + MainContainer.numCust;
	private boolean returned = false;
	
	public void setup() {
		for(int i = 0; i < 6; i++) {
			int book = 'a' + i;
			//System.out.println(Character.toString((char) book));
			books.add(Character.toString((char) book));
		}
		int n = rand.nextInt(6);
		ACLMessage msg = new ACLMessage( ACLMessage.INFORM );
		AID dest = new AID("Librarian", AID.ISLOCALNAME);
	    msg.addReceiver( dest );
		String str = senderID + " - Book Catalog";
		System.out.println(str);
		msg.setContent(str);
	    send(msg);
	    /*ACLMessage answer = receive();
        if (answer != null) {
        	System.out.println("recebeu");
        	str = senderID + " - Pretendo o livro " + books.get(n);
        }
        //str = senderID + " - Pretendo o livro " + books.get(n);
        msg.setContent(str);
        send(msg);
        System.out.println(str);
	    try {
	    	TimeUnit.SECONDS.sleep(10000);
	    	str = senderID + " - Devolvo o livro " + books.get(n);
	    	msg.setContent(str);
	    	send(msg);
	    	System.out.println(str);
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }*/	    
	    addBehaviour(new SimpleBehaviour(this) {
	    	public void action() {
		    	ACLMessage answer = receive();
		        if (answer != null) {
		        	String str = senderID + " - I want the book " + books.get(n);
		            msg.setContent(str);
		            send(msg);
		            System.out.println(str);
		    	    try {
		    	    	TimeUnit.SECONDS.sleep(10);
		    	    	str = senderID + " - Returning book " + books.get(n);
		    	    	msg.setContent(str);
		    	    	send(msg);
		    	    	System.out.println(str);
		    	    	returned = true;
		    	    } catch(Exception e) {
		    	    	e.printStackTrace();
		    	    }
		        }
	        }

			@Override
			public boolean done() {
				if (returned) 
					return true;
				return false;
			}
        });
	}
	
	public void takeDown() {
		super.takeDown();
	}
}
