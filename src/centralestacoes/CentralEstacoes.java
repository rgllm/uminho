package centralestacoes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

public class CentralEstacoes extends Agent {
    
    private static ArrayList<String> estacoes;
    private static final String FILENAME = "estacoes.txt";
    private String senderID;
    
    @Override
    public void setup(){
        estacoes= new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
		estacoes.add(sCurrentLine);
            }
        } 
        catch (IOException e) {
        }
        addBehaviour(new CyclicBehaviour(this) {
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
                    if(op.equals("listar")){
                        ACLMessage todasEstacoes = new ACLMessage( ACLMessage.INFORM );
                        StringBuilder answer = new StringBuilder();
                        Object[] estacoes_list = estacoes.toArray();
                        answer.append(senderID).append("Estacoes: \n");
                        for(Object obj : estacoes_list) {
                            answer.append(obj.toString()).append('\n');
                	}
                        todasEstacoes.setContent(answer.toString());
                        AID dest = new AID(receiver, AID.ISLOCALNAME);
                        todasEstacoes.addReceiver(dest);
                        send(todasEstacoes);
                        System.out.println(answer);
                    }
                    
                }  
            block();   
            }
        });
	}
	
    @Override
    public void takeDown() {
        super.takeDown();
    }
}