package stacion;

import java.util.ArrayList;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class HeadBehav extends CyclicBehaviour {
	private static ArrayList<String> estacoes;
	
	public void add(String s) {
		
	}
	
	public void populate() {
		StationWriter s = new StationWriter();
		estacoes = s.readFile();
	}
	
	@Override
    public void action() {
		String senderID;
		ACLMessage msg= myAgent.receive();
        if(msg!=null){
            String[] parts = msg.getContent().split(" - ");
            String receiver = parts[0];
            String content = parts[1];
            String[] contentParts = content.split("\\s+");
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
                myAgent.send(todasEstacoes);
                System.out.println(answer);
            }
            
        }  
    block();   
    }

}
