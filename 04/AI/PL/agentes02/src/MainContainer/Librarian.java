package MainContainer;

import java.util.HashMap;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class Librarian extends Agent {
	
	class Book {
		boolean requested;
		int timesRequested;
		
		public Book() {
			requested = false;
			timesRequested = 0;
		}
	}
	private HashMap<String, Book> requests;
	private String senderID;
	
	public void setup() {
		requests = new HashMap<>();
		for(int i = 0; i < 4; i++) {
			int book = 'a' + i;			
			requests.put(Character.toString((char) book), new Book());
		}
		addBehaviour(new CyclicBehaviour(this) 
        {
             public void action() 
             {
                ACLMessage msg= receive();
                if (msg!=null) {
                	//System.out.println(msg.getContent());
                	//To split "Customer X" from the message itself
                    String[] parts = msg.getContent().split(" - ");
                    //To get the receiver
                    String receiver = parts[0];
                    //To check the incoming message
                    String content = parts[1];
                    String[] contentParts = content.split(" ");
                    String op = contentParts[0];
                    //Process operation
                    senderID = myAgent.getLocalName() + " - ";
                    if (op.equals("Which?")) {
                		//Announce available books
                		ACLMessage books_avail_msg = new ACLMessage( ACLMessage.INFORM );
                		Object[] books_list = requests.keySet().toArray();
                		StringBuilder answer = new StringBuilder();
                		answer.append(senderID + "Books: \n");
                		for(Object obj : books_list) {
                			answer.append(obj.toString() + '\n');
                		}
                		books_avail_msg.setContent(answer.toString());
                		AID dest = new AID(receiver, AID.ISLOCALNAME);
                		books_avail_msg.addReceiver(dest);
                		send(books_avail_msg);
                		System.out.println(answer);
                    }
                    else {
                    	String book = contentParts[contentParts.length - 1];
	                    if (op.equals("Want")) {
	                    	//Request book
	                    	Book b = requests.get(book);
	                    	String answer;
                    		ACLMessage answer_msg = new ACLMessage( ACLMessage.INFORM );
                    		answer_msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));
	                    	if (!requests.containsKey(book)) {
	                    		answer = senderID + "The book does not exist.";
	                    	} else {
	                    		b.timesRequested++;
	                    		if (b.requested) {
	                    			answer = senderID + "The book is not available.";
		                    	}
	                    		else {
	                    			answer = senderID + "The book is available.";
	                    			b.requested = true;
	                    		}
		                    	requests.put(book, b);
	                    	}
	                    	System.out.println(answer);
                    		answer_msg.setContent(answer);
                        	send(answer_msg);
	                    }
	                    if (op.equals("Return It")) {
	                    	//Return book
	                    	Book b = requests.get(book);
	                    	b.requested = false;
	                    	requests.put(book, b);
	                    	String answer = senderID + "Returned book: " + book;
                    		ACLMessage answer_msg = new ACLMessage( ACLMessage.INFORM );
                    		answer_msg.addReceiver(new AID(receiver, AID.ISLOCALNAME));
                    		answer_msg.setContent(answer);
	                    	System.out.println(answer);
	                    	send(answer_msg);
	                    }
                    }             
                }
                block();
             }
        });
	}
	
	public void takeDown() {
		super.takeDown();
	}
}
