/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes05;

import jadex.bdi.runtime.IExpression;
import jadex.bdi.runtime.IMessageEvent;
import jadex.bdi.runtime.Plan;

/**
 *
 * @author rgllm
 */
public class EnPtTranslationPlan extends Plan {
    String wordtotranslate;
    String wordtranslated;
    String reply;
    IExpression query;
        
    public EnPtTranslationPlan(){
        getLogger().info("Created: "+this);
        this.query = getExpression("query_epword");
    }
    
    @Override
    public void body() {
        
        IMessageEvent me = waitForMessageEvent("request_translation");
        IMessageEvent replymsg;
        
        int cnt = ((Integer) getBeliefbase().getBelief("contador").getFact());
        
        String words = (String) me.getParameter("content").getValue();
        String[] tokenizer = words.split(" ");
        
        if(tokenizer.length == 3){
            this.wordtotranslate = tokenizer[2];
            this.wordtranslated = (String) query.execute("$eword",wordtotranslate);

            if(wordtranslated != null){
                getLogger().info("Translation Completed! English word: "+wordtotranslate+" | Portuguese word: "+wordtranslated);
                reply="respond_translation";
            }else{
                getLogger().info("Translation Failed! Word: "+wordtotranslate+" not in database");
                reply="failure_translation";
            }
        }
        getBeliefbase().getBelief("contador").setFact(cnt+1);
        
        replymsg = getEventbase().createReply(me, reply);
        replymsg.getParameter("content").setValue(wordtranslated);
        sendMessage(replymsg);
        
        
    }
    
}
