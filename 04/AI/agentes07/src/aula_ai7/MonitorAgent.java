package aula_ai7;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.util.leap.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import jess.Context;
import jess.Fact;
import jess.Funcall;
import jess.JessException;
import jess.Rete;
import jess.Value;
import jess.ValueVector;

/**
 *
 * @author Rita Canavarro
 */

public class MonitorAgent extends Agent{
    private HashMap<String,List<Integer>> DepTemps; //guarda as temperaturas todas assoc a um departamento
    private HashMap<String,Double[]> DepInfo; //pos 0: nº de vezes que heating on ; pos 1: nº de vezes que cooling on; pos 2: mean ; pos 3: stdev
    private HashMap<String,AID> AIDInfo;
    
    @Override
    public void setup(){
        DepTemps = new HashMap<> ();
        DepInfo = new HashMap<> ();
        AIDInfo = new HashMap<> ();
                
        this.addBehaviour(new JessInfo(this,"aula_ai7/JadeAgent.clp"));
        this.addBehaviour(new informData(this,100000));
    }
    
   
    private class JessInfo extends CyclicBehaviour{
        private Rete engine;
 
        public JessInfo(Agent a,String filename){
            
            engine = new Rete();
            
            try {

                engine.addUserfunction(new JessSend(a,this));
                
                engine.batch(filename);
                
                engine.reset();
            } catch (JessException ex) {
                ex.printStackTrace();
            }
        }

        @Override
        public void action() {
            ACLMessage msg = receive();
           
           if(msg != null){
                try {

                      ACLMsg2Jess(msg);
                      engine.run();  

                } catch (JessException ex) {
                    ex.printStackTrace();
                }
           }else{
               block();
           }
        }
        
     public void saveAIDInfo(AID a){
        AIDInfo.put(a.getName(),a);
     }
        
      public AID getAIDfromInfo(String a){
        AID aid = (AID) AIDInfo.get(a);
            
        if(aid == null){
            return new AID(a,AID.ISLOCALNAME);
        }
            
        return aid;
      }
        
      public ArrayList<AID> getInfo(Context ctx, ValueVector vv){
            ArrayList<AID> aux = new ArrayList<> ();
           
            for(int i = 0;i < vv.size() ; i++){
                try {
                    aux.add(getAIDfromInfo(vv.get(i).stringValue(ctx)));
                } catch (JessException ex) {
                    Logger.getLogger(MonitorAgent.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            
            return aux;
      }
        
       /*
        * Asserts a Fact that represents the msg in Jess
       */
       public boolean addFact(String fact){
            try {
                engine.executeCommand(fact);
            } catch (JessException ex) {
                Logger.getLogger(MonitorAgent.class.getName()).log(Level.SEVERE, null, ex);
                return false;
            }

           return true;
       }
       
       /*
       * Convert a ACLMessage to a JESS Fact  
       */
       public boolean ACLMsg2Jess(ACLMessage msg){
            StringBuilder sb = new StringBuilder();
            int temp = 0;
            String name="";
            sb.append("(assert (ACLMessage ");
            
            sb.append("(communicative-act ").append(msg.getPerformative()).append(")");
           
            if(msg.getSender() != null){
                sb.append(" (sender ").append(msg.getSender().getName()).append(")");
                saveAIDInfo(msg.getSender());
                name=msg.getSender().getName();
                
                if(!DepTemps.containsKey(name)){
                DepTemps.put(name,new ArrayList<> ());
                }
            }
            
            Iterator it = msg.getAllReceiver();
            
            if(it.hasNext()){
                sb.append(" (receiver ");
                
                while(it.hasNext()){
                    AID receiver = (AID) it.next();
                    saveAIDInfo(receiver);
                    sb.append(receiver.getName()).append(" ");;
                }
                
                sb.append(")");
            }
            
            
            if(msg.getContent()!=null){
                temp = Integer.parseInt(msg.getContent());
                DepTemps.get(name).add(temp);
                sb.append("  (content ").append(temp).append(")");
            }
          
            sb.append("))");
            String jmsg = sb.toString();
            return addFact(jmsg);
       }
        
        /*
        * Transform the Jess fact to a ACLMessage
        */
        public ACLMessage Fact2ACL(Context context,ValueVector vv){
            ACLMessage msg = null;
            String name = "";
            try {
               
                /*PERFORMATIVE*/
                int performative = Integer.parseInt(vv.get(0).stringValue(context));
                msg = new ACLMessage(performative);
               
                /*SENDER*/
               
                AID dest = myAgent.getAID();
                msg.setSender(dest);
                name = dest.getName();
                
                if(!DepInfo.containsKey(name)){
                    DepInfo.put(name,new Double[4]);

                    for(int i = 0 ;i<4;i++){
                        DepInfo.get(name)[i]=0.0;
                    }
                }
                
                ArrayList<AID> receivers = getInfo(context,vv.get(2).listValue(context));
                
                for(AID aid : receivers){
                    msg.addReceiver(aid);
                }
                
                if(vv.get(10).stringValue(context).equals("heating")){
                    ++DepInfo.get(name)[0];
                }else if(vv.get(10).stringValue(context).equals("cooling")){
                    ++DepInfo.get(name)[1];

                }
                
                msg.setContent(vv.get(10).stringValue(context));
                
               
            } catch (JessException ex) {
                Logger.getLogger(MonitorAgent.class.getName()).log(Level.SEVERE, null, ex);
            }

             return msg;
        }

        
    }
    
    
       private class JessSend implements jess.Userfunction{
        Agent myagent;
        JessInfo ji;
        
        public JessSend(Agent a,JessInfo j){
            myagent = a;
            ji = j;
        }
        
        @Override
        public String getName() {
           return ("send");
        }
        
         @Override
        public Value call(ValueVector vv, Context cntxt) throws JessException {
            Fact f = vv.get(1).factValue(cntxt);
           //System.out.println(f);
            ACLMessage msg = ji.Fact2ACL(cntxt, f);
         //  System.out.println(msg.toString());
            myagent.send(msg);

            return Funcall.TRUE;
        }
        
    }
    
    
    private class informData extends TickerBehaviour{
        
        public informData(Agent a,long time){
            super(a,time);
        }
        
        public void calculateMean(){
            double sum = 0.0;
            List<Integer> aux;

            for(Map.Entry<String,List<Integer>> kp : DepTemps.entrySet()){
                aux = kp.getValue();
                
                for (Integer aux1 : aux) {
                    sum += aux1;
                }
                
                Double[] get = DepInfo.get(kp.getKey()); 
                get[2]= sum/aux.size();
                
                DepInfo.put(kp.getKey(),get);
            }
        }
        
        public void calculateSTDev(){
          double stdev = 0.0;
          double mean;
          List<Integer> aux;
          Double[] info;
          
          for(Map.Entry<String,Double[]> kp: DepInfo.entrySet()){
              aux = DepTemps.get(kp.getKey());
              info = kp.getValue();
              mean = info[2];
              
              for(Integer auxl:aux){
                  stdev += Math.pow(auxl-mean,2);
              }
              
              info[3]=Math.sqrt(stdev/aux.size());
              DepInfo.put(kp.getKey(),info);
          }
          
        }
        
        public HashMap<String,Double[]> top3Heating(){
           List list = new LinkedList(DepInfo.entrySet());
           int i = 0;
           Collections.sort(list,new Comparator(){
               @Override
               public int compare(Object o1,Object o2){
                   Double[] x = (Double[]) ((Map.Entry) (o1)).getValue();
                   Double[] y = (Double[])  ((Map.Entry) (o2)).getValue();
                   return ((Comparable) x[0]).compareTo(y[0]);
               }
           }
           );
           
           HashMap<String,Double[]> aux = new LinkedHashMap();
           
           for (java.util.Iterator it = list.iterator();it.hasNext();){
               Map.Entry entry = (Map.Entry) it.next();
               if(i < 3){
                   aux.put((String)entry.getKey(),(Double[])entry.getValue());
               }else{
                   break;
               }
               i++;
           }
           
           return aux;
        }
       
        public HashMap<String,Double[]> top3Cooling(){
        List list = new LinkedList(DepInfo.entrySet());
           int i = 0;
           Collections.sort(list,new Comparator(){
               @Override
               public int compare(Object o1,Object o2){
                   Double[] x = (Double[]) ((Map.Entry) (o1)).getValue();
                   Double[] y = (Double[])  ((Map.Entry) (o2)).getValue();
                   return ((Comparable) x[1]).compareTo(y[1]);
               }
           }
           );
           
           HashMap<String,Double[]> aux = new LinkedHashMap();
           
           for (java.util.Iterator it = list.iterator();it.hasNext();){
               Map.Entry entry = (Map.Entry) it.next();
               if(i < 3){
                   aux.put((String)entry.getKey(),(Double[])entry.getValue());
               }else{
                   break;
               }
               i++;
           }
           
           return aux;
        }

        @Override
        protected void onTick() {
            calculateMean();
            calculateSTDev();
            
            HashMap<String,Double[]> top3C = top3Cooling();
            HashMap<String,Double[]> top3H = top3Heating();
            
            System.out.println("TOP 3 DEPS COLLING");
            if(top3C.isEmpty()){
                System.out.println("Ainda não temos dados suficientes");
            }else{
                for(Map.Entry<String,Double[]> kp: top3C.entrySet()){
                    System.out.println(kp.getKey() + " Mean: "+kp.getValue()[2]+" StDev: "+kp.getValue()[3]);
                }
            }
            System.out.println("TOP 3 DEPS HEATING");
            if(top3H.isEmpty()){
                System.out.println("Ainda não temos dados suficientes");
            }else{
                for(Map.Entry<String,Double[]> kp: top3H.entrySet()){
                    System.out.println(kp.getKey() + " Mean: "+kp.getValue()[2]+" StDev: "+kp.getValue()[3]);
                }
            }
        }
        
        
    }
}
