package stacion;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class StationWriter {
	
    private static final String FILENAME = "data/stacions.txt";
	
	public ArrayList<String> readFile() {
		ArrayList<String> estacoes= new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
            	estacoes.add(sCurrentLine);
            }
        } 
        catch (IOException e) {
        }
        return estacoes;
	}
}
