package SA.team;


public class Accuracy {
	
	private int shotLinear;
	private int shotLinearSuc;
	private int shotCurv;
	private int shotCurvSuc;
	private int shotSimple;
	private int shotSimpleSuc;
	
	private int type; // 1 for linear, 2 for curve, 0 for simple
	
	public Accuracy(){
		this.shotLinear = 1;
		this.shotLinearSuc = 0;
		this.shotCurv = 1;
		this.shotCurvSuc = 0;
		this.shotSimple = 1;
		this.shotSimpleSuc = 0;
		this.type = 1;
	}

	public double getType(){
		return type;
	}
	
	public void addShot(){
		if(type == 0){
			shotSimple++;
		}
		else if(type == 1){
			shotLinear++;
		}
		else if(type == 2){
			shotCurv++;
		}
		
		if((shotLinearSuc/ shotLinear) > (shotCurvSuc /shotCurv)){
			if((shotLinearSuc/ shotLinear) > (shotSimpleSuc/ shotSimple)){
				type = 1;
			}
			else{
				type = 0;
			}
		}
		else if((shotLinearSuc/ shotLinear) < (shotCurvSuc /shotCurv)){
			if( (shotCurvSuc /shotCurv) > (shotSimpleSuc/ shotSimple)){
				type = 2;
			}
			else{
				type = 0;
			}
		}
	}

}