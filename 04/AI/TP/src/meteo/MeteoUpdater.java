package meteo;

import jade.core.Agent;

@SuppressWarnings("serial")
public class MeteoUpdater extends Agent {
    
    @Override
    public void setup(){
        this.addBehaviour(new UpdateMeteo(this,5000));
    }
}