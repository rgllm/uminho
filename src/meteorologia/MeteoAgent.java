package meteorologia;
import java.util.ArrayList;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;

public class MeteoAgent extends Agent {
    
   private ArrayList<String> list = new ArrayList<String>() {{add("CHUVA");add("QUENTE");add("FRIO");add("NEVOEIRO");add("TROVOADA");}};
    private String atualMode="QUENTE";
    
    
    @Override
    public void setup(){
        this.addBehaviour(new updateMeteo(this,5000));
        this.addBehaviour(new sendMeteo());
    }
    
    private class updateMeteo extends TickerBehaviour{
        
        public updateMeteo(Agent t,long time){
            super(t,time);
        }
        
        @Override
        protected void onTick(){
            Random rand = new Random();
            int pos = rand.nextInt(list.size())-1;
            atualMode = list.get(pos);   
        }
        
    }
    
    private class sendMeteo extends CyclicBehaviour{
        private String senderID;
        
        @Override
        public void action() {
            ACLMessage msg= receive();
                if(msg!=null){
                    String[] parts = msg.getContent().split(" - ");
                    String receiver = parts[0];
                    String content = parts[1];
                    String[] contentParts = content.split(" ");
                    String op = contentParts[0];
                    senderID = myAgent.getLocalName() + " - ";
                    if(op.equals("info")){
                        ACLMessage meteo = new ACLMessage( ACLMessage.INFORM );
                        StringBuilder answer = new StringBuilder();
                        answer.append(senderID).append(atualMode);
                        AID dest = new AID(receiver, AID.ISLOCALNAME);
                        meteo.setContent(answer.toString());
                        meteo.addReceiver(dest);
                        send(meteo);
                        System.out.println(answer);
                    }
                    
                }  
            block();   
            }
    }

}