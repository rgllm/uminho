package meteo;

import java.util.ArrayList;
import java.util.Random;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class SendMeteo extends CyclicBehaviour{
    private String senderID;
    private String atualMode="QUENTE";
	private ArrayList<String> list = new ArrayList<String>() {{add("CHUVA");add("QUENTE");add("FRIO");add("NEVOEIRO");add("TROVOADA");}};
	
    
    @Override
    public void action() {
        ACLMessage msg= myAgent.receive();
            if(msg!=null){
                if(msg.getContent().charAt(0)=='M'){
                    ACLMessage meteo = new ACLMessage( ACLMessage.INFORM );
                    StringBuilder answer = new StringBuilder();
                    answer.append(senderID).append(atualMode);
                    AID dest = msg.getSender();
                    meteo.setContent("W " + answer.toString());
                    meteo.addReceiver(dest);
                    myAgent.send(meteo);
                    System.out.println(answer);
                }
                if(msg.getContent().charAt(0)=='*') {
                	Random rand = new Random();
                    int pos = rand.nextInt(list.size())-1;
                    if(pos < 0) pos = 0;
                    atualMode = list.get(pos);   
                }
            }  
        block();   
        }
}
