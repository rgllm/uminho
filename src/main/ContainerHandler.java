package main;

import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class ContainerHandler {
	
	public ContainerController createContainer(MainContainer c,String containerName){
		ContainerController newcontainer = c.initMainContainerInPlatform("localhost", "9888", containerName);
		return newcontainer;
	}

	public AgentController addAgentToContainer(String name,String type, ContainerController n){
		try {
			Object[] o = new Object[1];
			o[0] = n;
			AgentController ag = n.createNewAgent(name, type, o);
			ag.start();
			return ag;
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
		return null;
	}

}
