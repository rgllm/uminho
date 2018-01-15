package com.rgllm.trader;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.InputMismatchException;

public class Menu {
    
    private List<String> opcoes;
    private int op;
    
    public Menu(String[] opcoes) {
        this.opcoes = new ArrayList<>();
        for (String op : opcoes) 
            this.opcoes.add(op);
        this.op = 0;
    }

    public void executa() {
        do {
            showMenu();
            this.op = lerOpcao();
        } while (this.op == -1);
    }

    
    private void showMenu() {
        for (int i=0; i<this.opcoes.size(); i++) {
            System.out.print(i+1);
            System.out.print(" - ");
            System.out.println(this.opcoes.get(i));
        }
        System.out.println("0 - Quit");
    }
    
 private int lerOpcao() {
        int op; 
        Scanner is = new Scanner(System.in);
        
        System.out.print("Option: ");
        try {
            if(is.hasNextInt()) {op = is.nextInt();}
            else op=-1;
        }
        catch (InputMismatchException e) { // Não foi inscrito um int
            op = -1;
        }
        if (op<0 || op>this.opcoes.size()) {
            System.out.println("Not valid!");
            op = -1;
        }
        return op;
    }

    public int getOpcao() {
        return this.op;
    }
    
}