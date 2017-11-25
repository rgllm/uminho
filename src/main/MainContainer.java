package main;


import jade.core.Runtime;

import java.util.ArrayList;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.ContainerController;
import stacion.StationWriter;
import user.Mother;

public class MainContainer {

	Runtime rt;
	ContainerController container;

	public ContainerController initMainContainerInPlatform(String host, String port, String containerName) {

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
		return container;
	}
	
	
	public static void main(String[] args) {
		MainContainer a = new MainContainer();
		ContainerHandler cont = new ContainerHandler();
		ContainerController usr = cont.createContainer(a,"SuperBike");
		
		

		//tratar de ler o ficheiro aqui tambem e criar os agentes das estações todas
		cont.addAgentToContainer("stacionHead", "stacion.Head", usr);
		StationWriter sw = new StationWriter();
		ArrayList<String> stations = sw.readFile();
		 for (String name : stations) { 		      
			 cont.addAgentToContainer(name, "stacion.Stacion", usr); 		
	      }
		 //inserir metereologia
		 cont.addAgentToContainer("Meteo", "meteo.MeteoAgent", usr);
		 cont.addAgentToContainer("MeteoUpdater", "meteo.MeteoUpdater", usr);
		 //inserir decider
		 cont.addAgentToContainer("Decider", "decider.Decider", usr);
		
		
		
		
		Mother mother = new Mother(usr,1000 );
		mother.giveBirth();
		
        
        }
}