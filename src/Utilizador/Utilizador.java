package agents;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import java.util.Random;
/**
 *
 * @author Tiago
 */
public class Utilizador extends Agent {
    
    private String deciderID;
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
    
    Utilizador(String deciderID) {
        this.deciderID = deciderID;
    }
    
    @Override
    public void setup(){
        setValues();
        //this.addBehaviour(new sendMessage1());
        //sleep ??
        //this.addBehaviour(new sendMessage2());
    }
    
    private class sendMessage1 extends OneShotBehaviour {
        @Override
        public void action() {
            ACLMessage data = new ACLMessage( ACLMessage.INFORM );
            senderID = myAgent.getLocalName() + " - ";
            StringBuilder answer = new StringBuilder();
            answer.append(senderID).append(state).append(" - ").append(age);
            answer.append(" - ").append(sex).append(" - ").append(health);
            answer.append(" - ").append(condition).append(" - ").append(xini).append(" - ").append(yini);
            answer.append(" - ").append(xfin).append(" - ").append(yfin);
            AID dest = new AID(deciderID, AID.ISLOCALNAME);
            data.setContent(answer.toString());
            data.addReceiver(dest);
            send(data);
        }
    }
    
    private class sendMessage2 extends OneShotBehaviour {
        @Override
        public void action() {
            ACLMessage data = new ACLMessage( ACLMessage.INFORM );
            senderID = myAgent.getLocalName() + " - ";
            StringBuilder answer = new StringBuilder();
            answer.append(senderID).append(state).append(" - ").append(age);
            answer.append(" - ").append(sex).append(" - ").append(health);
            answer.append(" - ").append(condition).append(" - ").append(xini).append(" - ").append(yini);
            answer.append(" - ").append(xfin).append(" - ").append(yfin);
            AID dest = new AID(deciderID, AID.ISLOCALNAME);
            data.setContent(answer.toString());
            data.addReceiver(dest);
            send(data);
        }
    }    
    
    private void setValues() {
        Random rand = new Random(System.currentTimeMillis());
        double r = rand.nextDouble();
        //20% das idades compreendidas entre os 12 e 16
        if (r < .20) {
            this.age = rand.nextInt(16-12)+12;
        } 
        //60% das idades compreendidas entre os 17 e 60
        else if (r < .8) {
            this.age = rand.nextInt(60-17)+17;
        } 
        //20% das idades compreendidas entre os 61 e 80
        else {
            this.age = rand.nextInt(80-61)+61;
        }
        
        this.sex = rand.nextInt(2)+1;
        
        double r1 = rand.nextDouble();
        //15% dos estados são depressivos
        if (r1 < .15) {
            this.state = 1;
        } 
        //70% dos estados são tristes, contentes ou neutros
        else if (r1 < .85) {
            this.state = rand.nextInt(4-2)+2;
        } 
        //15% dos estados são eufóricos
        else {
            this.state = 5;
        }
        if (this.age < 50) {
            double r2 = rand.nextDouble();
            //15% das condições fisicas são passivas
            if (r2 < .15) {
                this.condition = 1;
            } 
             //70% das condições fisicas são pouco ativos, ativos ou atletas
            else if (r2 < .85) {
                this.condition = rand.nextInt(4-2)+2;
            } 
            //15% das condições fisicas são atletas profissionais
            else {
                this.condition = 5;   
            }     
        }
        else {
            double r2 = rand.nextDouble();
            //35% das condições fisicas são passivas
            if (r2 < .35) {
                this.condition = 1;
            } 
             //60% das condições fisicas são pouco ativos, ativos ou atletas
            else if (r2 < .95) {
                this.condition = rand.nextInt(4-2)+2;
            } 
            //5% das condições fisicas são atletas profissionais
            else {
                this.condition = 5;   
            }
        }
        if (this.condition == 5 || this.condition == 4) {
            double r3 = rand.nextDouble();
            //80% são saudáveis
            if (r3 < .80) {
                this.health = 1;
            } 
             //5% têm doenças graves
            else if (r3 < .85) {
                this.health = 2;
            } 
            //15% têm doenças leves
            else {
                this.health = 3;   
            }
        }
        else {
            double r3 = rand.nextDouble();
            //60% são saudáveis
            if (r3 < .60) {
                this.health = 1;
            } 
             //15% têm doenças graves
            else if (r3 < .85) {
                this.health = 2;
            } 
            //25% têm doenças leves
            else {
                this.health = 3;   
            }
        }
        setCoordinates();
    }
    
    private void setCoordinates() {
        Random rand = new Random(System.currentTimeMillis());
        //gera valores random entre 0 e 30
        this.xini = rand.nextFloat()*30;
        this.yini = rand.nextFloat()*30;
        //criar estes pontos a uma certa distancia dos pontos iniciais
        this.xfin = rand.nextFloat()*30;
        this.yfin = rand.nextFloat()*30;
    }
    
    @Override
    public void takeDown() {
        super.takeDown();
    }
}
