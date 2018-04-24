package SA.team;

public class CircularIntercept extends Intercept {

	protected Coordinate getEstimatedPosition(double time) {
		if (Math.abs(angularVelocity_rad_per_sec) <= Math.toRadians(0.1)) {
  			return super.getEstimatedPosition(time);
 		}
 
    		double initialTargetHeading = Math.toRadians(targetHeading);
    		double finalTargetHeading   = initialTargetHeading +  angularVelocity_rad_per_sec * time;
		double x = targetStartingPoint.getX() - targetVelocity / angularVelocity_rad_per_sec * (Math.cos(finalTargetHeading) - Math.cos(initialTargetHeading));
    		double y = targetStartingPoint.getY() - targetVelocity / angularVelocity_rad_per_sec * (Math.sin(initialTargetHeading) - Math.sin(finalTargetHeading));
    		return new Coordinate(x,y);
	}
 
}