package aula_ai7;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import java.util.Random;

/**
 *
 * @author Rita Canavarro
 */

public class TemperatureSensorAgent extends Agent {
    private int initialTemp = 25;
    private int tempupdate;
    private Random rand;
    private boolean flag = false;
    private String mode;
    
    
    @Override
    public void setup(){
        rand = new Random();
        this.addBehaviour(new updateTemp(this,5000));
        this.addBehaviour(new sendTemp(this,20000));
        this.addBehaviour(new changeTemp());
    }
    
    private class updateTemp extends TickerBehaviour{
        
        public updateTemp(Agent t,long time){
            super(t,time);
        }
        
        @Override
        protected void onTick() {
            if(flag){
                if(mode.equals("Heating")){
                    tempupdate = 1;
                    //System.out.println("AC");
                }else if(mode.equals("Cooling")){
                    tempupdate = -1;
                    // System.out.println("AC");
                }
                
            }else{
                
             tempupdate = rand.nextInt(3) - 1;
             
            }
            
            initialTemp += tempupdate;
        }
        
    }
    
    private class sendTemp extends TickerBehaviour{
        String text;
        
        public sendTemp(Agent t,long time){
            super(t,time);
        }
        
        @Override
        protected void onTick(){
            AID dest = new AID("MonitorAgent",AID.ISLOCALNAME);
            ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
            msg.addReceiver(dest);
            text = Integer.toString(initialTemp);
            msg.setContent(text);
            send(msg);
            
        }
    }
    
    private class changeTemp extends CyclicBehaviour{

        @Override
        public void action(){
           ACLMessage msg = receive();
           String content;
           
           if(msg != null){
               content = msg.getContent();
               //System.out.println(content);
               if(content.equals("Heating")){
                   flag = true;
                   mode = "Heating";
               }else if(content.equals("Cooling")){
                   flag = true;
                   mode = "Cooling";
               }
           }
           block();
        }
        
    }
    
    @Override
    protected void takeDown(){
        super.takeDown();
        System.out.println("Departamento encerrou!");
    }
}
