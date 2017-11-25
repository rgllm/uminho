package user;

import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class UserBehav extends OneShotBehaviour {
    
    private String senderID;
    private int age;
    private int state;
    private int sex;
    private int condition;
    private int health;
    private float xini;
    private float yini;
    private float xfin;
    private float yfin;
    
	
	public UserBehav(String senderID, int age, int state, int sex, int condition, int health, float xini, float yini,
			float xfin, float yfin) {
		this.senderID = senderID;
		this.age = age;
		this.state = state;
		this.sex = sex;
		this.condition = condition;
		this.health = health;
		this.xini = xini;
		this.yini = yini;
		this.xfin = xfin;
		this.yfin = yfin;
	}

	@Override
    public void action() {
        ACLMessage data = new ACLMessage( ACLMessage.INFORM );
        senderID = myAgent.getLocalName() + " ";
        StringBuilder answer = new StringBuilder();
        answer.append(senderID).append(state).append(" ").append(age);
        answer.append(" ").append(sex).append(" ").append(health);
        answer.append(" ").append(condition).append(" ").append(xini).append(" ").append(yini);
        answer.append(" ").append(xfin).append(" ").append(yfin);
        AID dest = new AID("Decider", AID.ISLOCALNAME);
        data.setContent(answer.toString());
        data.addReceiver(dest);
        myAgent.send(data);
        System.out.println("User: "+answer.toString());
        myAgent.doDelete();
	}
}

