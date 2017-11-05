/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes05;

import jadex.bdi.runtime.Plan;

/**
 *
 * @author rgllm
 */
public class NotifyPlan extends Plan {
    public NotifyPlan(){
        getLogger().info("Created:"+this);
    }

    @Override
    public void body() {
       int cnt = ((Integer) getBeliefbase().getBelief("contador").getFact());
       long alarme = (long) getBeliefbase().getBelief("alarme").getFact();
       getBeliefbase().getBelief("alarme").setFact(alarme+10000);
       
       getLogger().info("There have been "+cnt+" requests!");

    }
}

