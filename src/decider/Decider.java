package decider;

import jade.core.Agent;

@SuppressWarnings("serial")
public class Decider extends Agent{
	
	@Override
    public void setup(){
		this.addBehaviour(new JessInfo(this,"decider/Decider.clp"));
    }
}
