/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashSet;

public class LeituraVendas {
    
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
        { System.out.println(e.getMessage()); return null; };
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
    
    public static ArrayList<Venda> leituraVendas(String fich){
       ArrayList<Venda> vendas = new ArrayList <>();
        
       try{
           vendas=parseAllLinhas(readLinesWithBuff(fich));
        }
        catch(NullPointerException e){
            System.out.println("You have to have a file.\n");
        }
      
    return vendas;
    }
}
