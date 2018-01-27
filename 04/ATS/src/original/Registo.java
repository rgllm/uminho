
/**
 * Write a description of class Registo here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
import java.io.IOException;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.HashMap;
public class Registo{
     public void executa(Utilizadores utilizadores,HashMap<String,Veiculo> veiculos) {
        int i=0;
        Scanner scin = new Scanner(System.in);
        
        System.out.println("\n-----------Tipo de registo-----------");
        System.out.println("1 - Motorista ");
        System.out.println("2 - Cliente ");
        System.out.println("3 - Veiculo ");
        System.out.print("Opção: ");
        try {
            i = scin.nextInt();
        }
        catch (InputMismatchException e) {
            i=0;
        }

        
        if (i!=1 && i!=2 && i!=3)
            System.out.println("Opção Inválida!!!");
        else{
            if(i==1)
                registarMotorista(utilizadores);
            else if(i==2)
                registarCliente(utilizadores);
            else
                registarVeiculo(veiculos);
                
            System.out.println("Registo efetuado com sucesso!");
        }

    }
    
    private void registarCliente(Utilizadores utilizadores){
        String email,nome,password,morada,data;
        Scanner scin = new Scanner(System.in);
        
        System.out.println("-----------Dados de registo-----------");
        System.out.print("Email: ");
        email = scin.nextLine();
        
        System.out.print("Nome: ");
        nome = scin.nextLine();
        
        System.out.print("Password: ");
        password = scin.nextLine();
        
        System.out.print("Morada: ");
        morada = scin.nextLine();
        
        System.out.print("Data de nascimento(dd-mm-aaaa): ");
        data = scin.nextLine();
        
        boolean x;
        do{
            x=false;
            try{
                utilizadores.adiciona(email,nome,password,morada,data);
            }
            catch(EmailAlreadyInUseException e){
                System.out.println("Email já em uso.");
                System.out.print("Insira um novo email: ");
                email = scin.nextLine();
                x=true;
            }
        }while(x);
                

    }
    
    private void registarMotorista(Utilizadores utilizadores){
        String email,nome,password,morada,data;
        int i=0;
        Scanner scin = new Scanner(System.in);
        
        System.out.println("-----------Dados de registo-----------");
        System.out.print("Email: ");
        email = scin.nextLine();
        
        System.out.print("Nome: ");
        nome = scin.nextLine();
        
        System.out.print("Password: ");
        password = scin.nextLine();
        
        System.out.print("Morada: ");
        morada = scin.nextLine();
        
        System.out.print("Data de nascimento(dd-mm-aaaa): ");
        data = scin.nextLine();
        
        boolean x;
        do{
            x=false;
            try{
                utilizadores.adiciona(email,nome,password,morada,data,null);
            }
            catch(EmailAlreadyInUseException e){
                System.out.println("Email já em uso.");
                System.out.print("Insira um novo email: ");
                email = scin.nextLine();
                x=true;
            }
        }while(x);
        

    }
    
    private void registarVeiculo(HashMap<String,Veiculo> veiculos){
        int vm=0;
        String matricula;
        double pb=0;
        int i=0;
        
        Scanner scin = new Scanner(System.in);
        System.out.println("-----------Dados sobre o veículo-----------");
        
        while(i!=1 && i!=2 && i!=3){
            System.out.println("Tipo de veículo: ");
            System.out.println("1 - Carro");
            System.out.println("2 - Carrinha");
            System.out.println("3 - Mota");
            System.out.print("Opção: ");
            
            try {
                i = scin.nextInt();
            }
            catch (InputMismatchException e) {
                i = 0;
            }
            if (i!=1 && i!=2 && i!=3) {
                System.out.println("Opção Inválida!!!");
                i = 0;
            }
        }
        
        do{
            System.out.print("Velocidade média: ");

            try {
                vm = scin.nextInt();
            }
            catch (InputMismatchException e) {
                vm = 0;
            }
            if (vm<0) {
                System.out.println("Número Inválido!!!");
            }    
        }while(vm<0);
  
        do{
            System.out.print("Preço base: ");

            try {
                pb = scin.nextInt();
            }
            catch (InputMismatchException e) {
                pb = 0;
            }
            if (pb<0) {
                System.out.println("Preço Inválido!!!");
            }    
        }while(pb<0);
        scin.nextLine();

        System.out.print("Matricula: ");
        matricula = scin.nextLine();
        
        boolean x;
        do{
            x=false;
            if(veiculos.containsKey(matricula)){
                System.out.println("Matricula já em uso.");
                System.out.print("Insira uma nova matricula: ");
                matricula = scin.nextLine();
                x=true;
            }
        }while(x);
        

        
        if(i==1)
            veiculos.put(matricula,new Carro(vm,pb,100,matricula,new Coordenada(),false));
        else if(i==2)
            veiculos.put(matricula,new Carrinha(vm,pb,100,matricula,new Coordenada(),false));
        else
            veiculos.put(matricula,new Mota(vm,pb,100,matricula,new Coordenada(),false));
    }
}
