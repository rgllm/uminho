package stacion;

import jade.core.Agent;


@SuppressWarnings("serial")
public class Head extends Agent {
    @Override
    public void setup(){
        addBehaviour(new HeadBehav());
	}
	
    @Override
    public void takeDown() {
        super.takeDown();
    }
}