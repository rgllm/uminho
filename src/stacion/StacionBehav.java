package stacion;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

@SuppressWarnings("serial")
public class StacionBehav extends CyclicBehaviour  {

	int bikeNumber;
	int bikeAvail;
	float area;
	float price;
	float[] pos = new float[2];
	int state;
	
	public StacionBehav(int bikeNumber, float area, float price, float x, float y) {
		this.bikeNumber = bikeNumber;
		this.bikeAvail = bikeNumber;
		this.area = area;
		state = 1;
		this.price = price;
		pos[0] = x;
		pos[1] = y;
	}
	
	@Override
	public void action() {
		ACLMessage msg = myAgent.receive();
		if(msg != null){

			if (msg.getContent().charAt(0)=='+') {
				ACLMessage inf = new ACLMessage(ACLMessage.PROPOSE);
				inf.addReceiver(msg.getSender());
				if (bikeAvail != bikeNumber) {
					bikeAvail++;
					if(bikeAvail >= 0.75 * bikeNumber && state != 2) {
						area = (float) 0.75 * area;
						state = 2;
					}
					else if(bikeAvail <= 0.25 * bikeNumber && state != 1) {
						area = (float) (1/1.5) * area;
						state = 1;
					}
	
					inf.setContent("AvailBike!");
					
				}
				else {
					inf.setContent("NotAvailBike");
				}
				myAgent.send(inf);
			}	
			
			else if (msg.getContent().charAt(0)=='-') {
				ACLMessage inf = new ACLMessage(ACLMessage.PROPOSE);
				inf.addReceiver(msg.getSender());
				if (bikeAvail != 0) {
					bikeAvail--;
					if(bikeAvail <= 0.25 * bikeNumber && state != 0) {
						area = (float) 1.5 * area;
						state = 0;
					}
					else if(bikeAvail >= 0.75 * bikeNumber && state != 1) {
						area = (float) (1/0.75) * area;
						state = 1;
					}
					inf.setContent("AvailBike!");
				}
				else {
					inf.setContent("NotAvailBike");
				}
				myAgent.send(inf);
			}
		}
	}
}
