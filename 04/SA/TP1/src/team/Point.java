package SA.team;

import java.io.*;

public class Point implements Serializable {
	
	private double x;
	private double y;
	private double power;

	
	private static final long serialVersionUID  = 7526472295622776149L;

	public Point(double x, double y, double power){
		this.x = x;
		this.y = y;
		this.power = power;
	}

	public double getX(){
		return x;
	}
	
	public double getY(){
		return y;
	}
	
	public double getPower(){
		return power;
	}
	
	public String toString(){
		return x + " " + y + " " + power +  "\n ";
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