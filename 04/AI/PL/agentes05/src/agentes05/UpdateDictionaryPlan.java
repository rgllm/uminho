/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes05;

import jadex.bdi.runtime.IExpression;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;
import jadex.commons.Tuple;
/**
 *
 * @author rgllm
 */
public class UpdateDictionaryPlan extends Plan {
    String reply;
    String content;
    String enword;
    String ptword;
    IExpression query;

    public UpdateDictionaryPlan(){
         getLogger().info("Created: "+this);
         this.query = getExpression("query_epword");
    }
    
    
    @Override
    public void body() {
        IMessageEvent me = waitForMessageEvent("request_addentry");
        IMessageEvent replymsg;
        
        String word = (String) me.getParameter("content").getValue();
        String words[] = word.split(" ");
        
         int cnt = ((Integer) getBeliefbase().getBelief("contador").getFact());
        
        if(words.length==4){
            enword = words[2];
            ptword = words[3];
            
            Object test = query.execute("$eword",enword);
            
            if(test == null){
                getBeliefbase().getBeliefSet("epwords").addFact(new Tuple(enword,ptword));
                getLogger().info("Pair of words added to  database: "+enword+"-"+ptword);
                reply = "inform_addentry";
                content = ptword;
            }else{
                getLogger().info("Pair of words are already in the database!");
                reply="failure_addentry";
                content ="Word: "+ptword+" already in the database";
            }
        }else{
            getLogger().warning("Format invalid!");
        }
        
         getBeliefbase().getBelief("contador").setFact(cnt+1);
        
        replymsg = getEventbase().createReply(me, reply);
        replymsg.getParameter("content").setValue(content);
        sendMessage(replymsg);
    }

}
