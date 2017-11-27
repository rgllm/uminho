package decider;

public class Pair {
	private int accept;
	private int reject;
	
	public Pair(int a, int r) {
		accept = a;
		reject = r;
	}
	
	public int getAccept() {
		return accept;
	}
	
	public int getReject() {
		return reject;
	}
	
	public Pair incA() {
		accept++;
		return this;
	}
	
	public Pair incR() {
		reject++;
		return this;
	}
}
