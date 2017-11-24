package decider;

import java.util.HashMap;
import java.util.Map.Entry;

public class Node {
	private HashMap<String,Node> nodos;
	private int last;
	private int users;
	private String type; 
	private String subtype;
	
	public Node(int l, String t, String s) {
		this.users = 0;
		this.last = l;
		this.nodos = new HashMap<String,Node>();
		this.type = t;
		this.subtype = s;
	}
	
	public int getUsers() {
		return users;
	}
	
	public int last() {
		return last;
	}
	
	public HashMap<String, Node> getNext() {
		return nodos;
	}
	
	public String getType() {
		return type;
	}
	
	public String getSubType() {
		return subtype;
	}
	
	public void add(Node n) {
		if(last == 0)
			users++;
		else {
			for(Entry<String, Node> entry : nodos.entrySet()) {
				entry.getValue().add(n);
			}
		}
	}
	
	public void create(Node n) {
		if(last == 0)
			nodos.put(n.subtype,n);
		else {
			for(Entry<String, Node> entry : nodos.entrySet()) {
				entry.getValue().create(n);
			}
		}
	}
	
}
