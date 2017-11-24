package decider;

public class Case {
	private String stateOfMind;
	private int age;
	private String sex;
	private String sick;
	private String phisic;
	private float xi;
	private float yi;
	private float xd;
	private float yd;
	
	
	public Case(String s) {
		String[] parts = s.split("\\s+");
		this.stateOfMind = parts[0];
		this.age = Integer.parseInt(parts[1]);
		this.sex = parts[2];
		this.sick = parts[3];
		this.phisic = parts[4];
		this.xi = Float.parseFloat(parts[5]);
		this.yi = Float.parseFloat(parts[6]);
		this.xd = Float.parseFloat(parts[7]);
		this.yd = Float.parseFloat(parts[8]);
	}
	
	public String getState() {
		return stateOfMind;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public String getSex() {
		return sex;
	}
	
	public String getSick() {
		return sick;
	}
	
	public String getPhisic() {
		return phisic;
	}
	
	public Float getXi() {
		return xi;
	}
	
	public Float getYi() {
		return yi;
	}
	
	public Float getXd() {
		return xd;
	}
	
	public Float getYd() {
		return yd;
	}
}
