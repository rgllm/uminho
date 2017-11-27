/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package agentes04;

import jadex.bridge.ComponentIdentifier;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.IComponentStep;
import jadex.bridge.IInternalAccess;
import jadex.bridge.fipa.SFipa;
import jadex.bridge.service.types.message.MessageType;
import jadex.commons.SUtil;
import jadex.commons.future.Future;
import jadex.commons.future.IFuture;
import jadex.micro.MicroAgent;
import jadex.micro.annotation.Agent;
import jadex.micro.annotation.AgentBody;
import jadex.micro.annotation.Description;
import jadex.micro.testcases.stream.ReceiverAgent;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple agent to be used as a basis for own developments.
 */
@Agent
@Description("Ping Agent.")
public class PingAgent extends MicroAgent{
	/**
	 * Called when the agent is started.
	 */
        @SuppressWarnings("unchecked")
        public IFuture<Void> executeBody() { // Function called after Agent being started;
            final Future<Void> ret = new Future<Void>();
            
            IComponentStep step = new IComponentStep() {
                public IFuture<Void> execute(IInternalAccess ia) { // Execute step ...
                   System.out.println("Ping Agent!");
                    String convid = SUtil.createUniqueId(PingAgent.this.getAgentName());
                    Map<String, Object> msg = new HashMap<String, Object>();
                    msg.put("content", "ping");
                    msg.put("performative", "query-if");
                    msg.put("conversation_id", convid);
                    msg.put("receivers", new IComponentIdentifier[] {new ComponentIdentifier
				("PongAgent", getComponentIdentifier().getParent())});
				PingAgent.this.sendMessage(msg, SFipa.FIPA_MESSAGE_TYPE);
                   
                    return IFuture.DONE;
                }
            };
        scheduleStep(step);
        return ret;
        }
        
public void messageArrived(Map<String, Object> msg, MessageType mt) {
		if ((boolean) msg.get("performative").equals("query-if")) {
			System.out.println("Message received: " +msg.get("content"));
			String convid = SUtil.createUniqueId(PingAgent.this.getAgentName());
			Map<String, Object> msg2 = new HashMap<String, Object>();
			msg2.put("content", "ping");
			msg2.put("performative", "query-if");
			msg2.put("conversation_id", convid);
			msg2.put("receivers", new IComponentIdentifier[] {new ComponentIdentifier
			("PongAgent", getComponentIdentifier().getParent())});
			PingAgent.this.sendMessage(msg2, SFipa.FIPA_MESSAGE_TYPE);
		}
	}
}
