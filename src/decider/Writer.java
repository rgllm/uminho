package decider;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class Writer {
	
	private HashMap<String,HashMap<String,Pair>> words = new HashMap<String,HashMap<String,Pair>>();
	private String filename = "data/cases.txt";
	
	public HashMap<String,HashMap<String,Pair>> populate() {
		//ler o ficheiro
		File file = new File(filename);
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
		return words;
	}
	
	private void parser(String text) {
		// TODO Auto-generated method stub
		String[] splited = text.split("\\s+");
		Boolean one = Boolean.parseBoolean(splited[5]);
		Boolean two = Boolean.parseBoolean(splited[6]);
		parserLoop("spirit",splited[0], one, two);
		parserLoop("age",splited[1], one, two);
		parserLoop("sex",splited[2], one, two);
		parserLoop("healt",splited[3], one, two);
		parserLoop("phisic",splited[4], one, two);
		parserLoop("weather",splited[5], one, two);

	}
	
	private void parserLoop(String n, String t, Boolean one, Boolean two) {
		HashMap<String, Pair> tmp = words.get(t);
		if(tmp==null || !words.containsKey(n)) {
			words.put(n, new HashMap<String,Pair>() );
		}
		if(!words.get(n).containsKey(t)) {
			words.get(n).put(t, new Pair(0,0));
		}
		if(one) {
			words.get(n).put(t,words.get(n).get(t).incA());
		}
		else {
			words.get(n).put(t,words.get(n).get(t).incR());
		}
		if(two) {
			words.get(n).put(t,words.get(n).get(t).incA());
		}
		else {
			words.get(n).put(t,words.get(n).get(t).incR());
		}
		
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
