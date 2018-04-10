package SA.team;

import java.util.ArrayList;
import java.io.*;

public class Orders implements Serializable{

	private ArrayList<Order> orders;
	private static final long serialVersionUID  = 7526472295622772247L;
	

	public Orders(){
		this.orders = new ArrayList<Order>();
	}

	public void addOrder(Order order){
		this.orders.add(order);
	}

	public int getSize(){
		return this.orders.size();
	}

	public Order getOrder(int n){
		return orders.get(n);
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
