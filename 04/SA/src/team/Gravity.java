package SA.team;

import  java.util.TreeMap;
import java.util.Map;
import java.util.Random;

public class Gravity {

	public Gravity(){}
	final double PI = Math.PI;
	
	
	public Point getTrajectory(TreeMap<String,Point> points, double x, double y, double width, double height,String name){
		double xforce = 0;
		double yforce = 0;
		double force;
		double ang;
		Point p;
		Random random = new Random();
		int n = random.nextInt(20)+1;
			
		TreeMap<String,Point> n_points = new TreeMap<String,Point>(points);
		
		for(int i=0;i<n;i++){
			p = new Point( width * random.nextDouble(), height * random.nextDouble(), 10 * random.nextDouble());
			n_points.put("#Random "+i,p);
		}
		    for(Map.Entry<String,Point> point : n_points.entrySet()) {
			p = (Point) point.getValue();
			
			if(point.getKey().charAt(0) == '#'){
				force = p.getPower()/Math.pow(getRange(x,y,p.getX(),p.getY()),1.5);
			}
			else if(point.getKey().charAt(0) == '_'){
				force = p.getPower()/Math.pow(getRange(x,y,p.getX(),p.getY()),3);
			}
			else{
				if(!point.getKey().equals(name)){
					force = p.getPower()/Math.pow(getRange(x,y,p.getX(),p.getY()),3);
				}
				else {
					force = 0;
				}
			}
		        	ang = normaliseBearing(Math.PI/2 - Math.atan2(y - p.getY(), x - p.getX())); 
			xforce += Math.sin(ang) * force;
		        	yforce += Math.cos(ang) * force;
		}
		     
		//The following four lines add wall avoidance. 
		xforce += 5000/Math.pow(getRange(x, y, width, y), 3);
		xforce -= 5000/Math.pow(getRange(x, y, 0, y), 3);
		yforce += 5000/Math.pow(getRange(x, y, x, height), 3);
		yforce -= 5000/Math.pow(getRange(x, y, x, 0), 3);
		     
		//Move in the direction of our resolved force.
		return new Point(x-xforce, y-yforce, 0);
		}
	
	//Returns the distance between two points
	private double getRange(double x1,double y1, double x2,double y2) {
		double x = x2-x1;
		double y = y2-y1;
		double range = Math.sqrt(x*x + y*y);
		return range;   
	}
	
	public double normaliseBearing(double ang) {
		if (ang > PI)
			ang -= 2*PI;
		if (ang < -PI)
			ang += 2*PI;
		return ang;
	}
	
	public double absBearing(double x1, double y1, double x2, double y2) {
		double xo = x2-x1;
		double yo = y2-y1;
		double hyp = Math.sqrt(xo*xo + yo*yo);
		double arcSin = Math.toDegrees(Math.asin(xo / hyp));
		double bearing = 0;
	
		if (xo > 0 && yo > 0) { // both pos: lower-Left
			bearing = arcSin;
		} else if (xo < 0 && yo > 0) { // x neg, y pos: lower-right
			bearing = 360 + arcSin; // arcsin is negative here, actuall 360 - ang
		} else if (xo > 0 && yo < 0) { // x pos, y neg: upper-left
			bearing = 180 - arcSin;
		} else if (xo < 0 && yo < 0) { // both neg: upper-right
			bearing = 180 - arcSin; // arcsin is negative here, actually 180 + ang
		}

		return bearing;
	}
}
