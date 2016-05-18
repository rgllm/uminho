import java.io.*;
import java.util.*;

public class ImoobiliariaApp {

    // Construtor privado (não queremos instâncias!...)
    private ImoobiliariaApp() {}

    private static Imoobiliaria tab;
    private static Menu menumain, menuRU; //Menus da aplicação

    // Método principal
    public static void main(String[] args) throws ClassNotFoundException, UtilizadorExistenteException {
        carregarMenus();
        initApp();
        stall();
        do {
            menumain.executa();
            switch (menumain.getOpcao()) {
                case 1: inserirUser();
                        break;
                /*case 2: iniciaSessao();
                        break;
                case 3: setEstado();
                        break;
                case 4: totalGestores();
                        break;
                case 5: totalPorTipo();
                        break;
                case 6: totalKms(); */
            }
        } while (menumain.getOpcao()!=0);
        try{
            tab.gravaObj("estado.im");
        }
        catch(IOException e){
            System.out.println("Não consegui gravar os Dados");
        }
        System.out.println("Até breve!....");
    }

    private static void carregarMenus() {
        String[] ops = {"Registar Utilizador"};
        String [] opsUtilizador = {"Adicionar Vendedor","Adicionar Comprador"};

        menumain = new Menu(ops);
        menuRU = new Menu(opsUtilizador);
    }

    /*private static void carregarDados() {
        try {
            tab = Imoobiliaria.initApp("estado.tabemp");
        }
        catch (IOException e) {
            tab = new Imoobiliaria();
            System.out.println("Não consegui ler os dados!\nErro de leitura.");
        }
        catch (ClassNotFoundException e) {
            tab = new Imoobiliaria();
            System.out.println("Não consegui ler os dados!\nFicheiro com formato desconhecido.");
        }
        catch (ClassCastException e) {
            tab = new Imoobiliaria();
            System.out.println("Não consegui ler os dados!\nErro de formato.");
        }
    } */

    public static void initApp (){
        carregarDados();
    }
    
    private static void carregarDados() {
        try {
            tab = Imoobiliaria.leObj("estado.im");
        }
        catch (IOException e) {
            tab = new Imoobiliaria();
            System.out.println("Não consegui ler os dados!\nErro de leitura.");
        } 
        catch (ClassNotFoundException e) {
           tab = new Imoobiliaria();
            System.out.println("Não consegui ler os dados!\nFicheiro com formato desconhecido.");
        }
        catch (ClassCastException e) {
           tab = new Imoobiliaria();
            System.out.println("Não consegui ler os dados!\nErro de formato."); 
        }
    }

    private static void inserirUser() throws UtilizadorExistenteException {
        Utilizador u=null;
        Scanner scin = new Scanner(System.in);

        menuRU.executa();
        if (menuRU.getOpcao() != 0) {
            String email,nome,password,morada,data_nascimento;

            System.out.print("Email: ");
            email = scin.nextLine();
            System.out.print("Nome: ");
            nome = scin.nextLine();
            System.out.print("Password ");
            password = scin.nextLine();
            System.out.print("Morada ");
            morada = scin.nextLine();
             System.out.print("Data de Nascimento");
            data_nascimento = scin.nextLine();
            switch (menuRU.getOpcao()) {
                case 1: u = new Vendedor(email,nome,password,morada,data_nascimento);
                        break;
                case 2: u = new Comprador(email,nome,password,morada,data_nascimento);
                        break;
            }
            tab.registarUtilizador(u);
        } else {
            System.out.println("Inserção cancelada!");
        }
        scin.close();
    }

    public static void stall(){
       try{
           Thread.sleep(1000);
       }
       catch(Exception e){
       }
   }

    }
