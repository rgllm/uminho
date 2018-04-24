package SA.team;
import robocode.*;

import static robocode.util.Utils.normalRelativeAngleDegrees;
import robocode.HitWallEvent;

public class Destroyer extends TeamRobot implements Droid{
	
	private boolean not_order = true;
	private TeamInfo gameInfo;

	public void run() {
		gameInfo = new TeamInfo(getBattleFieldWidth(), getBattleFieldHeight());
		setAdjustGunForRobotTurn(true);
		while(true){
			if(not_order){
				avoid_bullets();	
			}
		}
	}
	
	public void avoid_bullets(){
		if(gameInfo != null){
			Gravity g = new Gravity();
			Point p = g.getTrajectory(gameInfo.getGravityMap(), getX(), getY(), getBattleFieldWidth(), getBattleFieldHeight(),getName());
			double x = ((p.getX() - getX()) * 100) % getBattleFieldWidth();
			double y = ((p.getY() - getY()) *100) % getBattleFieldHeight();
			goTo(x,y);
			execute();
		}
	}

	public void onMessageReceived(MessageEvent e) {
		if (e.getMessage() instanceof Orders) {
			Orders orders = (Orders) e.getMessage();
			not_order = false;
			for(int i=0;i!=orders.getSize();i++){
				orderDo(orders.getOrder(i));
			}
			not_order = true;
		}
		else if(e.getMessage() instanceof TeamInfo){
			this.gameInfo = (TeamInfo) e.getMessage();		
		}
	}

	public void orderDo(Order order){
		if(order.getType() == 0){
			orderFire(order);
		}
		else if(order.getType() == 1){
			orderWalk(order);
		}
		else if(order.getType() == 2){
			orderTurn(order);
		}
	}

	public void orderFire(Order order){
		double dx = order.getX() - this.getX();
		double dy = order.getY() - this.getY();
	
		double theta = Math.toDegrees(Math.atan2(dx, dy));
		turnGunRight(normalRelativeAngleDegrees(theta - getGunHeading()));
	
		
		fire(3);
	}

	public void orderWalk(Order order){
		boolean chegou = false;
		double x = order.getX();
		double y = order.getY();
        		
		while (!chegou) {
           		goTo(x, y);
			if (getX() == x && getY() == y) {
				chegou = true;
		            	stop();
            		}
        		}
	}
	
	
    private void goTo(double x, double y) {
        double dx = x - getX();
        double dy = y - getY();
        double angleToTarget = Math.atan2(dx, dy);
        double targetAngle = angleToTarget - getHeadingRadians();
        double distance = Math.hypot(dx, dy);
        double turnAngle = Math.atan(Math.tan(targetAngle));
        setTurnRightRadians(turnAngle);
        if (targetAngle == turnAngle) {
            setAhead(distance);
        } else {
            setBack(distance);
        }
    }

	public void orderTurn(Order order){
		double angles = order.getX();
		turnRight(angles-getHeading());
	}
	
	public void onDeath(DeathEvent e){
		try{
			broadcastMessage(getName()); 
		}
		catch(Exception n){}
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
