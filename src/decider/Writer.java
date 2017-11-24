package decider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

public class Writer {
	
	private Node nodos;
	private HashMap<String,HashMap<String,Integer>> words = new HashMap<String,HashMap<String,Integer>>();
	private HashMap<Integer,String> order = new HashMap<Integer,String>();
	
	public Node populate() {
		//ler o ficheiro
		File file = new File("cases.txt");
		BufferedReader reader = null;
		
		if(file != null) {
			try {
				reader = new BufferedReader(new FileReader(file));
			    String text = null;
	
			    while ((text = reader.readLine()) != null) {
			        parser(text);
			    }
			} catch(Exception e) {}
		}
		return createThree();
	}
	
	public Entry<String,Integer> maxEntry(){
		//ver qual a classe da primeira cena
				Entry<String, Integer> maxEntry = null;
				for(Entry<String, HashMap<String, Integer>> en1 : words.entrySet()) {
					HashMap<String, Integer> m = en1.getValue();
					for (Entry<String, Integer> en2 : m.entrySet()){
					    if (maxEntry == null || en2.getValue().compareTo(maxEntry.getValue()) > 0){
					        maxEntry = en2;
					        order.put(order.size()+1, en1.getKey());
					    }
					}
				}
			return maxEntry;
		}

	@SuppressWarnings({ "unchecked", "unlikely-arg-type" })
	public Node createThree() {
		int i = 0;
		//criar nodo inicial vazio
		nodos = new Node(0,"Init","Struct");
		//começar a adicionar nodos 
		Node tmp;
		while (words.size()!=0) {
			i++;
			for(Entry<String, HashMap<String, Integer>> en1 : words.entrySet()) {
				if(en1.getValue().containsKey(maxEntry().getKey())) {
					for(Entry<String, Integer> en2 : ((HashMap<String,Integer>) en1).entrySet()) {
						tmp = new Node(i,en1.getKey(),en2.getKey());
						nodos.create(tmp);
					}
					words.remove(en1);
				}
			}
		}
		//adicionar valores na arvore
		File file = new File("cases.txt");
		BufferedReader reader = null;
		
		if(file != null) {
			try {
				reader = new BufferedReader(new FileReader(file));
			    String text = null;
	
			    while ((text = reader.readLine()) != null) {
			    	String[] splited = text.split("\\s+");
					nodos.add(new Node(1,order.get(1),splited[0]));
					nodos.add(new Node(2,order.get(2),splited[1]));
					nodos.add(new Node(3,order.get(3),splited[2]));
					nodos.add(new Node(4,order.get(4),splited[3]));
					nodos.add(new Node(5,order.get(5),splited[4]));
			    }
			} catch(Exception e) {}
		}
		
		return nodos;
	}
	
	
	
	
	private void parser(String text) {
		// TODO Auto-generated method stub
		String[] splited = text.split("\\s+");
		parserLoop("spirit",splited[0]);
		parserLoop("age",splited[1]);
		parserLoop("sex",splited[2]);
		parserLoop("healt",splited[3]);
		parserLoop("phisic",splited[4]);
	}
	
	private void parserLoop(String n, String t) {
		HashMap<String, Integer> tmp = words.get(t);
		if(tmp==null) {
			words.put(t, null);
		}
		
		if(!words.get(t).containsKey(n)) {
			words.get(t).put(n, 0);
		}
		words.get(t).put(n,words.get(t).get(n)+1);
	}

	public void add(String s, String file) {
		try {
			BufferedWriter output;
			output = new BufferedWriter(new FileWriter(file,true));
			output.append(s);
			output.close();
		} catch (IOException e) {}
	}
}
