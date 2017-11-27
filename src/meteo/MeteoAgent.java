package meteo;
import jade.core.Agent;

@SuppressWarnings("serial")
public class MeteoAgent extends Agent {
    
    @Override
    public void setup(){
        this.addBehaviour(new SendMeteo());
    }
}