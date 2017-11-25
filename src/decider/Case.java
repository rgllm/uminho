package decider;

public class Case {
	private String stateOfMind;
	private int age;
	private String sex;
	private String sick;
	private String phisic;
	private Boolean acceptInit;
	private Boolean acceptEnd;
	
	
	public Case(String s) {
		String[] parts = s.split("\\s+");
		this.stateOfMind = parts[0];
		this.age = Integer.parseInt(parts[1]);
		this.sex = parts[2];
		this.sick = parts[3];
		this.phisic = parts[4];
		this.acceptInit = Boolean.parseBoolean(parts[5]);
		this.acceptEnd = Boolean.parseBoolean(parts[6]);
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
	
	public Boolean getAcceptInit() {
		return acceptInit;
	}
	
	public Boolean getAcceptEnd() {
		return acceptEnd;
	}
}
