package helloAgent;

import jade.core.Agent;

public class helloAgent extends Agent {
    
    @Override 
    protected void setup() {
        super.setup();
        
        System.out.println("HELLO");
        
    }
    
    @Override
    protected void takeDown() {
        // TODO Auto-generated method stub
        super.takeDown();
    }
    
}