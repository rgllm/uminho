package aula_ai7;

import jade.core.Runtime;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

/**
 * 
 */


public class MainContainer {

	Runtime rt;
	ContainerController container;

	public void initMainContainerInPlatform(String host, String port, String containerName) {

		//Get the JADE runtime interface (singleton)
		this.rt = Runtime.instance();
		
		//Create a Profile, where the launch arguments are stored
		Profile prof = new ProfileImpl();
		prof.setParameter(Profile.CONTAINER_NAME, containerName);
		prof.setParameter(Profile.MAIN_HOST, host);
		prof.setParameter(Profile.MAIN_PORT, port);
		prof.setParameter(Profile.MAIN, "true");
		prof.setParameter(Profile.GUI, "true");
		
		//create a main agent container
		this.container = rt.createMainContainer(prof);
		rt.setCloseVM(true);
	}
	
	public void startAgentInPlatform(String name, String classpath){
	    try {
	       AgentController ac = container.createNewAgent(
	  name, 
	  classpath, 
	  new Object[0]);
	       ac.start();
	    } catch (Exception e) {
	       e.printStackTrace();
	    }
	 }
	
	
	public static void main(String[] args) {
		MainContainer a = new MainContainer();
		
		a.initMainContainerInPlatform("localhost","9888","MainContainer");
                  
                
                 a.startAgentInPlatform("MonitorAgent", "aula_ai7.MonitorAgent");
                 a.startAgentInPlatform("TemperatureSensorAgent1", "aula_ai7.TemperatureSensorAgent");
                 a.startAgentInPlatform("TemperatureSensorAgent2", "aula_ai7.TemperatureSensorAgent");
                 a.startAgentInPlatform("TemperatureSensorAgent3", "aula_ai7.TemperatureSensorAgent");
        }
}