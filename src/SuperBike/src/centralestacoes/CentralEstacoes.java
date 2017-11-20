package centralestacoes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
* Ir buscar estações a um ficheiro
* Carregar para a memória
* Recebe um listar
* envia a lista de estações
*/


public class CentralEstacoes {
    
    public static ArrayList<String> estacoes;
    private static final String FILENAME = "estacoes.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        estacoes= new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(FILENAME))) {
            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null) {
		estacoes.add(sCurrentLine);
            }
        } 
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
