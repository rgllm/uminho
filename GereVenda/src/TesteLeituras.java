/**
 *
 * @author munybt, rgllm
 */

/*
import java.io.BufferedReader;
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
    
    public static ArrayList<String> readLinesWithBuff(String fich) {
        ArrayList<String> linhas = new ArrayList<>();
        BufferedReader inStream = null;
        String linha = null;
        try {
            inStream = new BufferedReader(new FileReader(fich));
        while( (linha = inStream.readLine()) != null )
            linhas.add(linha);
        }
        catch(IOException e)
        { out.println(e.getMessage()); return null; };
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
    ArrayList<Venda> res = new ArrayList<>();
    
    
    for(String s : linhas){
        res.add(parseLinhaVenda(s));
    }
    
    return res;

}

public static HashSet<Venda> parseAllLinhasToSet(ArrayList<String> linhas) {
    HashSet<Venda> res = new HashSet<>();
    Venda aux;
    int count=0;
    
    for(String s : linhas){
        aux= new Venda(parseLinhaVenda(s));
        if(!res.contains(aux))
            res.add(aux);
        else count++;
    }
    System.out.println("linhas repetidas : "+count);
    
    return res;

}

public static ArrayList<Venda> readVendasWithBuff(String fich) throws NullPointerException {
    int count=0;
    ArrayList<String> linhas = readLinesWithBuff(fich);
    ArrayList<Venda> vendas = parseAllLinhas(linhas);
    //HashSet<Venda> vendas = parseAllLinhasToSet(linhas);
    return vendas;
}

public static int nVendasPorFilial(int filial,ArrayList<Venda> vendas){
    int count=0;
    for(Venda v : vendas){
        if(v.getFilial()==filial)
            count++;
    }
    return count;
}

public static int nVendasPrecoZero(ArrayList<Venda> vendas){
    int count=0;
    for(Venda v : vendas){
        if(v.getPreco()==0.0)
            count++;
    }
    return count;
}

public static void main(String [] args){
    ArrayList<Venda> vendas=new ArrayList<>();
    try{
        Crono.start();
        vendas=readVendasWithBuff("Vendas_3M.txt");
        Crono.stop();
        System.out.println("Compras da filial 1: "+nVendasPorFilial(3,vendas));
        System.out.println("Compras preco 0: "+nVendasPrecoZero(vendas));
        System.out.println("Tempo: " + Crono.print());
        System.out.println("Linhas lidas: "+vendas.size());
    }
    catch(NullPointerException e){
        System.out.println("You have to have a file.\n");
    }
}


}
*/