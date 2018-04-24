package SA.team;

import robocode.ScannedRobotEvent;
import java.io.*;

public class State implements Serializable {
	
	private double energy;
	private double bearing;
	private double distance;
	private double heading;
	private double velocity;
	private double x;
	private double y;
	
	private static final long serialVersionUID  = 7526472295622776148L;

	public State(double energy, double bearing, double distance, double heading, double velocity){
		this.energy = energy;
		this.bearing = bearing;
		this.distance = distance;
		this.heading = heading;
		this.velocity = velocity;
		
		double angle = Math.toRadians((heading + bearing) % 360);
		this.x = (int)(x + Math.sin(angle) * distance);
 		this.y = (int)(y + Math.cos(angle) * distance);
	}
	
	public State(ScannedRobotEvent e){
		this.energy = e.getEnergy();
		this.bearing = e.getBearing();
		this.distance = e.getDistance();
		this.heading = e.getHeading();
		this.velocity = e.getVelocity();
		
		double angle = Math.toRadians((heading + e.getBearing()) % 360);
		this.x = (int)(x + Math.sin(angle) * e.getDistance());
 		this.y = (int)(y + Math.cos(angle) * e.getDistance());
	}

	public double getEnergy(){
		return this.energy;
	}

	public void setEnergy(double energy){
		this.energy = energy;
	}

	public double getBearing(){
		return this.bearing;
	}

	public void setBearing(double bearing){
		this.bearing = bearing;
	}

	public double getDistance(){
		return this.distance;
	}

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getHeading(){
		return this.heading;
	}

	public void setHeading(double heading){
		this.heading = heading;
	}

	public double getVelocity(){
		return this.velocity;
	}

	public void setVelocity(double velocity){
		this.velocity = velocity;
	}

	public boolean lowHealt(State s){
		if(this.energy > s.getEnergy()){
			return true;
		}
		else return false;
	}
	
	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
		
	   private void readObject(
		ObjectInputStream aInputStream
   		) throws ClassNotFoundException, IOException {
     		//always perform the default de-serialization first
     		aInputStream.defaultReadObject();
  	}

    	/**
	    * This is the default implementation of writeObject.
	    * Customise if necessary.
    	*/
    	private void writeObject(
    		  ObjectOutputStream aOutputStream
		    ) throws IOException {
		      //perform the default serialization for all non-transient, non-static fields
     		 aOutputStream.defaultWriteObject();
    	}
}