/**
 *
 * @author munybt, rgllm
 */

import gerevenda.Venda;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import java.util.*;

public class TesteLeituras {
    
    public static ArrayList<String> readLinesArrayWithScanner(String ficheiro) {
        ArrayList<String> linhas = new ArrayList<>();
        Scanner scanFile = null;
        try {
            scanFile = new Scanner(new FileReader(ficheiro));
            scanFile.useDelimiter("\n\r");
            while(scanFile.hasNext())
                linhas.add(scanFile.nextLine());
        }
        catch(IOException ioExc){ 
            out.println(ioExc.getMessage()); 
            return null; 
        } 
    finally { 
            if(scanFile != null) scanFile.close(); 
     } 
return linhas;
}

public static Venda parseLinhaVenda(String linha) {
    
    Venda lvenda;
    String produto,cliente;
    double preco;
    char modo;
    int mes,filial,unidades;
   
    String[] campos = linha.split(" ");
    try{
       produto=campos[0];
       preco=Double.parseDouble(campos[1]);
       unidades=Integer.parseInt(campos[2]);
       modo=campos[3].charAt(0);
       cliente=campos[4];
       mes=Integer.parseInt(campos[5]);
       filial=Integer.parseInt(campos[6]);
       lvenda=new Venda(produto,preco,unidades,modo,cliente,mes,filial);
    }
    catch(NumberFormatException | NullPointerException exc){
        return null;
    }
    
    return lvenda;
}

public static ArrayList<Venda> parseAllLinhas(ArrayList<String> linhas) {
    
    Iterator<String> it = linhas.iterator();
    String s;
    ArrayList<Venda> res = new ArrayList<>();
    
    
    while(it.hasNext()){
        s=it.next();
        res.add(parseLinhaVenda(s));
    }
   
    return res;

}

public static void main(String [] args){
    Crono.start();
    try{
    ArrayList<String> linhas = readLinesArrayWithScanner("Vendas_3M.txt");
    ArrayList<Venda> vendas = parseAllLinhas(linhas);
    Crono.stop();
    System.out.println("Tempo: " + Crono.print());
    }
    catch(NullPointerException){
        System.out.println("You have to have a file.\n");
    }
}


}
