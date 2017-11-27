package main;


import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class MainContainer {

	Runtime rt;
	ContainerController container;

	public ContainerController initContainerInPlatform(String host, String port, String containerName) {
		// Get the JADE runtime interface (singleton)
		this.rt = Runtime.instance();

		// Create a Profile, where the launch arguments are stored
		Profile profile = new ProfileImpl();
		profile.setParameter(Profile.CONTAINER_NAME, containerName);
		profile.setParameter(Profile.MAIN_HOST, host);
		profile.setParameter(Profile.MAIN_PORT, port);
		// create a non-main agent container
		ContainerController container = rt.createAgentContainer(profile);
		return container;
	}

	public void initMainContainerInPlatform(String host, String port, String containerName) {

		// Get the JADE runtime interface (singleton)
		this.rt = Runtime.instance();

		// Create a Profile, where the launch arguments are stored
		Profile prof = new ProfileImpl();
		prof.setParameter(Profile.CONTAINER_NAME, containerName);
		prof.setParameter(Profile.MAIN_HOST, host);
		prof.setParameter(Profile.MAIN_PORT, port);
		prof.setParameter(Profile.MAIN, "true");
		prof.setParameter(Profile.GUI, "true");

		// create a main agent container
		this.container = rt.createMainContainer(prof);
		rt.setCloseVM(true);

	}

	public void startAgentInPlatform(String name, String classpath) {
		try {
			AgentController ac = container.createNewAgent(name, classpath, new Object[0]);
			ac.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ContainerController createContainer(MainContainer c,String containerName){
		ContainerController newcontainer = c.initContainerInPlatform("localhost", "9888", containerName);
		return newcontainer;
	}
	
	public static void addAgentToContainer(String name,String type, ContainerController n){
		try {
			AgentController ag = n.createNewAgent(name, type, new Object[] {});// arguments
			ag.start();
		} catch (StaleProxyException e) {
			e.printStackTrace();
		}
	}
	
	

	public static void main(String[] args) {
		MainContainer a = new MainContainer();
		
		
		a.initMainContainerInPlatform("localhost", "9888", "MainContainer");
		ContainerController n = createContainer(a,"taxiCity");
		int i;
		addAgentToContainer("Manager","main.Manager",n);
		addAgentToContainer("Distance","distance.DistanceAgent",n);
		for(i=0;i!=5;i++){
			addAgentToContainer("taxi"+ i,"taxi.TaxiAgent",n);
		}
		for(i=0;i!=5;i++){
			addAgentToContainer("client"+ i,"client.ClientAgent",n);
		}
	}
}