package stacion;

public class Stacion{
	private String name;
	private int bikeCap;
	private int bikeAvail;
	private float areaToGo;
	private float priceToGo;
	private float areaToLeave;
	private float priceToLeave;
	private float x;
	private float y;
	private int state;
	
	protected Stacion(String n,int b, int ba,float a, float p, float xPos, float yPos){
		name = n;
		bikeCap = b;
		bikeAvail = ba;
		areaToGo = a;
		priceToGo = p;
		areaToLeave = a;
		priceToLeave = p;
		x = xPos;
		y = yPos;
		state = 2;
	}
	
	public Stacion(String s) {
		String[] splited = s.split("\\s+");
		name = splited[0];
		bikeCap = Integer.parseInt(splited[1]);
		bikeAvail = Integer.parseInt(splited[2]);
		priceToGo = Float.parseFloat(splited[3]);
		priceToLeave = Float.parseFloat(splited[3]);
		areaToGo = Float.parseFloat(splited[4]);
		areaToLeave = Float.parseFloat(splited[4]);
		x = Float.parseFloat(splited[5]);
		y = Float.parseFloat(splited[6]);
		state = 2;
	}
	
	public String toString() {
		StringBuilder answer = new StringBuilder();
		answer.append(name).append(" ");
		answer.append(bikeCap).append(" ");
		answer.append(bikeAvail).append(" ");
		answer.append(areaToGo).append(" ");
		answer.append(priceToGo).append(" ");
		answer.append(x).append(" ");
		answer.append(y).append(" ");
		
		return answer.toString();
	}
	
	public String getName() {
		return name;
	}
	
	public int getBikeCap() {
		return bikeCap;
	}
	
	public int getBikeAvail() {
		return bikeAvail;
	}
	
	public float getAreaGo() {
		return areaToGo;
	}
	
	public float getPrice(String state) {
		if(state.contains("Go")) {
			return priceToGo;
		}
		else
		return priceToLeave;
	}
	
	public float getAreaLeave() {
		return areaToLeave;
	}
	
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public Boolean addBike() {
		Boolean confirm = true;
		if(bikeCap<=bikeAvail) 
			confirm = false;
		else {
			bikeAvail++;
			if((bikeAvail/bikeCap >= 0.25 && state==1) || (bikeAvail/bikeCap >= 0.75 && state==2)) {
				areaToGo = (float) 2 * areaToGo;
				areaToLeave = (float)  areaToLeave / 2;
				priceToGo = (float) (priceToGo / 2);
				priceToLeave = (float) (priceToLeave * 2);
				if(bikeAvail/bikeCap >= 0.75 ) state = 3;
				else state = 2;
			}
		}
		return confirm;
	}
	
	public Boolean remBike() {
		Boolean confirm = true;
		if(0>=bikeAvail) 
			confirm = false;
		else {
			bikeAvail--;
			if((bikeAvail/bikeCap <= 0.25 && state==2) || (bikeAvail/bikeCap <= 0.75 && state==3)) {
				areaToGo = (float) 2 / areaToGo;
				areaToLeave = (float)  areaToLeave * 2;
				priceToGo = (float) (priceToGo * 2);
				priceToLeave = (float) (priceToLeave / 2);
				if(bikeAvail/bikeCap <= 0.25) state = 1;
				else state = 2;
			}
		}
		return confirm;
	}
}
