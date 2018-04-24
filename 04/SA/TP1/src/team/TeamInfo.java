package SA.team;

import  java.util.TreeMap;
import robocode.ScannedRobotEvent;
import java.io.*;

public class TeamInfo implements Serializable{
 
	private TreeMap<String,State> enemies;
	private TreeMap<String,State> team;
	private TreeMap<String,Point> gravityMap;
	private String priorityEnemy;
	private static final long serialVersionUID  = 7526472295622776147L;

	public TeamInfo(double width, double height){
		this.enemies = new TreeMap<String,State>();
		this.team = new TreeMap<String,State>();
		this.gravityMap = new TreeMap<String,Point>();
		Point p;
		for(int i=0;i<width;i=i+10){
			p = new Point(i, 0, -10);
			gravityMap.put("_Down_"+i,p);
			p = new Point(i, height, -10);
			gravityMap.put("_Up_"+i,p);
		}
		for(int i=0;i<height;i=i+10){
			p = new Point(0, i, -10);
			gravityMap.put("_Left_"+i,p);
			p = new Point(width,i, -10);
			gravityMap.put("_Right_"+i,p);
		}
	}

	public void addEnemy(String enemy, ScannedRobotEvent e, double heading, double x, double y){
		State state = new State(e);
		this.enemies.put(enemy,state);
		this.gravityMap.put(enemy, getPoint(e,heading,x,y,false));
		if (priorityEnemy== null){
			this.priorityEnemy = enemy;
			this.gravityMap.put(enemy, getPoint(e,heading,x,y,true));	
		}
		else if(this.enemies.get(priorityEnemy).lowHealt(state)){
			this.priorityEnemy = enemy;
		}
	}

	public void remEnemies(String enemy){
		this.enemies.remove(enemy);
		this.gravityMap.remove(enemy);
	}

	public State getEnemyState(String enemy){
		return this.enemies.get(enemy);
	}
	
	public TreeMap<String,State> getEnemies(){
		return enemies;
	}
	
	public void addTeamMember(String name, ScannedRobotEvent e, double heading, double x, double y){
		State state = new State(e);
		this.team.put(name,state);
		this.gravityMap.put(name,getPoint(e,heading,x,y,false));
	}

	public void remTeamMember(String name){
		this.team.remove(name);
		this.gravityMap.remove(name);
	}

	public State getTeamState(String name){
		return this.team.get(name);
	}
	
	public TreeMap<String,State> getTeam(){
		return team;
	}

	public void addPos(String name, Point point){
		this.gravityMap.put(name,point);
	}
	
	public void remPos(String name){
		this.gravityMap.remove(name);
	}
	
	public TreeMap<String,Point> getGravityMap(){
		return gravityMap;
	}
	
	private boolean isEnemy(String n){
		return enemies.containsKey(n);
	}
	
	private Point getPoint(ScannedRobotEvent e, double heading,double x, double y, boolean isPriority){
		double angle = Math.toRadians((heading + e.getBearing()) % 360);
		double scannedX = (int)(x + Math.sin(angle) * e.getDistance());
 		double scannedY = (int)(y + Math.cos(angle) * e.getDistance());
		
		if(isEnemy(e.getName())){
			if(isPriority){
				return new Point(scannedX, scannedY, 2);
			}
			else{
				return new Point(scannedX, scannedY, -5);
			}
		}
		else{
			return new Point(scannedX, scannedY, -100);
		}
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
