package SA.team;

import java.io.*;

public class Coordinate implements Serializable {
	
	private double x;
	private double y;
	private static final long serialVersionUID  = 7526472295622776189L;

	
	public Coordinate(double x, double y){
		this.x = x;
		this.y = y;
	}
	
	public Coordinate(){
		this.x = 0;
		this.y = 0;
	}
	
	public void set(double x, double y){
		this.x = x;
		this.y = y;
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
