package agentes04;

import java.util.HashMap;
import java.util.Map;

import jadex.bridge.ComponentIdentifier;
import jadex.bridge.IComponentIdentifier;
import jadex.bridge.fipa.SFipa;
import jadex.bridge.service.types.message.MessageType;
import jadex.commons.SUtil;
import jadex.micro.MicroAgent;

public class PongAgent extends MicroAgent {
	public void messageArrived(Map<String, Object> msg, MessageType mt) {
		if ((boolean) msg.get("performative").equals("query-if")) {
			System.out.println("Message received: " +msg.get("content"));
			String convid = SUtil.createUniqueId(PongAgent.this.getAgentName());
			Map<String, Object> msg2 = new HashMap<String, Object>();
			msg2.put("content", "pong");
			msg2.put("performative", "query-if");
			msg2.put("conversation_id", convid);
			msg2.put("receivers", new IComponentIdentifier[] {new ComponentIdentifier
			("PingAgent", getComponentIdentifier().getParent())});
			PongAgent.this.sendMessage(msg2, SFipa.FIPA_MESSAGE_TYPE);
		}
	}
}
