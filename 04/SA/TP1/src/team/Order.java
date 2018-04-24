package SA.team;

import java.io.*;

public class Order implements Serializable{

	private Integer type; // 0 para disparar, 1 para andar, 2 para rodar
	private double x;
	private double y;
	private static final long serialVersionUID  = 7526472295622776247L;


	public Order(Integer type, double x, double y){
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public Integer getType(){
		return this.type;
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