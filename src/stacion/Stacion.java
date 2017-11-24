package stacion;

import jade.core.Agent;


@SuppressWarnings("serial")
public class Stacion extends Agent {

	protected void setup(){
		int bikeNumber = 0;
		float area = 0;
		float price = 0;
		float x = 0;
		float y = 0;
		StacionBehav s = new StacionBehav(bikeNumber, area, price, x, y);
		addBehaviour(s);
	}
	
	protected void takeDown(){
		super.takeDown();
	}

}
