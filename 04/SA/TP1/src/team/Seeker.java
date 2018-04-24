package SA.team;
import robocode.*;

import robocode.ScannedRobotEvent;
import robocode.StatusEvent;
import java.util.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;

public class Seeker extends TeamRobot{
	
	private boolean team_not_dead = true;
	private String[] teamMates;
	private TeamInfo gameInfo;
	private boolean licenseToKill = false;
	private Accuracy ac;
	
	public void run(){
		//init
		teamMates = getTeammates();
		setAdjustGunForRobotTurn(true);
		setAdjustRadarForGunTurn(true);
		this.ac = new Accuracy();
		gameInfo = new TeamInfo(getBattleFieldWidth(), getBattleFieldHeight());
		
		while(team_not_dead){
			walk();
		}
		survive();	
	}
	
	public void onStatus(StatusEvent e){
		setTurnRadarRight(360);
	}
	
	public void onScannedRobot(ScannedRobotEvent e){
		if(licenseToKill){
			if(!checkTeamMate(e.getName())){
				gameInfo.addEnemy(e.getName(),e,getHeading(),getX(),getY());
				shotToKill(e.getName());
			}
		}else{
			if(!checkTeamMate(e.getName())){
				gameInfo.addEnemy(e.getName(),e,getHeading(),getX(),getY());
				kill(e.getName());
			}
			else{
				gameInfo.addTeamMember(e.getName(),e,getHeading(),getX(),getY());
			}
			try{
				broadcastMessage(gameInfo);
			}catch(Exception ex){}
		}
	}
	
	private boolean checkTeamMate(String n){
		for(int i=0;i<teamMates.length;i++){
			if (teamMates[i].equals(n)){
				return true;
			}
		}
		return false;
	}
	
	private void shotToKill(String e){
		Intercept intercept = new Intercept();
		State s = gameInfo.getEnemyState(e);

		intercept.calculate( this.getX(), this.getY(), s.getX(), s.getY(), s.getHeading(), s.getVelocity(),3,0);
		
		if ((intercept.impactPoint.getX() > 0) && (intercept.impactPoint.getX() < getBattleFieldWidth()) && (intercept.impactPoint.getY() > 0) && (intercept.impactPoint.getY() < getBattleFieldHeight())) {
    			orderFire(intercept.impactPoint.getX(),intercept.impactPoint.getY());
		}
	}	
	

	public void orderFire(double x, double y){
		double dx = x - this.getX();
		double dy = y - this.getY();
	
		double theta = Math.toDegrees(Math.atan2(dx, dy));
		turnGunRight(normalRelativeAngleDegrees(theta - getGunHeading()));
	
		double distance = Math.sqrt(dx*dx + dy*dy);
	
		if(distance > 200){
			fire(1);
		}
		else if (distance >100){
			fire(2);
		}
		else{
			fire(3);
		}
	}

	
	private void kill(String e){
		State s = gameInfo.getEnemyState(e);
		int bulPower;		

		if(s.getDistance()> 200){
			bulPower = 1;
		}
		else if (s.getDistance()> 100){
			bulPower = 2;
		}
		else{
			bulPower = 3;
		}
		if(ac.getType() == 1 || ac.getType() == 2){
		
			if(ac.getType() == 1){
				Intercept intercept = new Intercept();
				intercept.calculate( this.getX(), this.getY(), s.getX(), s.getY(), s.getHeading(), s.getVelocity(),bulPower,0);
						

				if ((intercept.impactPoint.getX() > 0) && (intercept.impactPoint.getX() < getBattleFieldWidth()) && (intercept.impactPoint.getY() > 0) && (intercept.impactPoint.getY() < getBattleFieldHeight())) {
		    			Orders msg = new Orders();
					Order kill = new Order(0, intercept.impactPoint.getX(), intercept.impactPoint.getY());
					msg.addOrder(kill);
					try{
						broadcastMessage(msg);
		  			}catch(Exception ex){}
				}

			}
			else{
				CircularIntercept intercept = new CircularIntercept();
				intercept.calculate( this.getX(), this.getY(), s.getX(), s.getY(), s.getHeading(), s.getVelocity(),bulPower,0);
						

				if ((intercept.impactPoint.getX() > 0) && (intercept.impactPoint.getX() < getBattleFieldWidth()) && (intercept.impactPoint.getY() > 0) && (intercept.impactPoint.getY() < getBattleFieldHeight())) {
		    			Orders msg = new Orders();
					Order kill = new Order(0, intercept.impactPoint.getX(), intercept.impactPoint.getY());
					msg.addOrder(kill);
					try{
						broadcastMessage(msg);
		  			}catch(Exception ex){}
				}

			}
			
		}
		else{
			Orders msg = new Orders();
			Order kill = new Order(0, s.getX(), s.getY());
			msg.addOrder(kill);
			try{
				broadcastMessage(msg);
	  		}catch(Exception ex){}
		}
	}

	private void walk(){
		Gravity g = new Gravity();
		Point p = g.getTrajectory(gameInfo.getGravityMap(), getX(), getY(), getBattleFieldWidth(), getBattleFieldHeight(),getName());
		goTo(p.getX(),p.getY());
		execute();
	}
	
	private void survive(){
		while(true){
			if(iCanWin()){
				licenseToKill = false;
				walk();
			}
			else{
				licenseToKill = true;
				walk();
			}
		}
	}
	
	private boolean iCanWin(){
		double enemiesEnergy = 0;
		double selfEnergy = 0;
		State state;
		for(Map.Entry<String,State> enemies : gameInfo.getEnemies().entrySet()) {
		        	state = (State) enemies.getValue();
			enemiesEnergy += state.getEnergy();
		}
		
		for(Map.Entry<String,State> team : gameInfo.getTeam().entrySet()) {
		        	state = (State) team.getValue();
			selfEnergy += state.getEnergy();
		}	
		
		if(selfEnergy > enemiesEnergy){ 
			return true;
		}
		else{
			return false;
		}
	}
	
	private void goTo(double x, double y) {
    		double dist = 20; 
		Gravity g = new Gravity();
    		double angle = Math.toDegrees(g.absBearing(getX(),getY(),x,y));
    		double r = turnTo(angle);
		setAhead(dist * r);
	}
 	
	public void onMessageReceived(MessageEvent e) {
		if (e.getMessage() instanceof String) {
			String vitim = (String) e.getMessage();
			gameInfo.remTeamMember(vitim);
			if(gameInfo.getTeam().size() < 3){
				team_not_dead = false;
			}
		}
	}
	
	public void onDeath(DeathEvent e){
		try{
			broadcastMessage(getName()); 
		}
		catch(Exception n){}
	}

	
	private int turnTo(double angle) {
    		double ang;
    		int dir;
		Gravity g = new Gravity();
    		ang = g.normaliseBearing(getHeading() - angle);
    		if (ang > 90) {
		        ang -= 180;
		        dir = -1;
		    }
		    else if (ang < -90) {
		        ang += 180;
		        dir = -1;
		    }
		    else {
		        dir = 1;
		    }
		    setTurnLeft(ang);
		    return dir;
	}	
	
	public void onHitWall(HitWallEvent e){
		setBack(50);
		turnRight(10);
	}

	public void onHitRobot(HitRobotEvent event) {
     		  if (event.getBearing() > -90 && event.getBearing() <= 90) {
           			setBack(100);
       		} else {
           			setAhead(100);
       		}
	}
	
	public void onHitByBullet(HitByBulletEvent e) {
		turnLeft(10);
	}

}

