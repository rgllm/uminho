package meteo;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class UpdateMeteo extends TickerBehaviour {
	   
	
    public UpdateMeteo(Agent t,long time){
        super(t,time);
    }
    
    @Override
    protected void onTick(){
    	ACLMessage inf = new ACLMessage(ACLMessage.INFORM);
		inf.addReceiver(new AID("Meteo", AID.ISLOCALNAME));
		inf.setContent("*");
		myAgent.send(inf);
    }

}
