package trader;

import java.io.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.*;

public class TraderApp{

    private static TraderInterface trader;
    private static Menu menumain,menuLogin,menuTradeType;
    
    /**
     * Cria uma nova instancia do TraderApp.
     * @param args input da consola 
     * @throws ClassNotFoundException, UtilizadorExistenteException, IOException, Exception
     */
    public static void main(String[] args) throws ClassNotFoundException, UtilizadorExistenteException, IOException, Exception{
		long startTimeNano = System.nanoTime( );
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		TraderApp main = new TraderApp();
    main.first();
    long time = threadMXBean.getCurrentThreadCpuTime();
    long taskTimeNano  = System.nanoTime( ) - startTimeNano; 
    System.out.println("CPU Time " + taskTimeNano);
    System.out.println("Elapsed Time " + time);
    }
    
    /**
     * Carrega os menus.
     * Invoca a funcao de leitura dos dados
     * Prepara a aplicacao para ser executada e apresenta  o menu inicial
     * @throws ClassNotFoundException, UtilizadorExistenteException, IOException, Exception
     */
    public void first() throws ClassNotFoundException, UtilizadorExistenteException, IOException, Exception {
        carregarMenus();
        initApp();
        System.out.println(trader.getUsers().toString());
        Thread updater = new Thread() {
            @Override
            public void run() {
                trader.portfolioUpdater();
            }
        };
        updater.start();
        do{
            Utils.printLogo();
            menumain.executa();
            switch(menumain.getOpcao()){
                case 1:
                    signIn();
                    break;
                case 2:
                    signUp();
                    break;
            }
        }while (menumain.getOpcao()!=0);
        try{
            SerializationUtil.serialize(trader,"trader.db");
        }
        catch(IOException e){
            System.out.println("Can't save data.");
        }
        System.out.println("See you soon!");
    } 
    
    /**
     * Menu que apresenta o menu login e espera pelo input do user
     * @throws Exception
     */
    public void menuLogin() throws Exception{
        Utils.printLogo();
        System.out.println("Balance: " + trader.userSaldo() + "$" + "\n");
        Scanner scan=new Scanner(System.in);
        do{
            menuLogin.executa();
            switch(menuLogin.getOpcao()){
                case 1:
                    tradeForm();
                    break;
                case 2:
                    closeTradeForm();
                    break;
                case 3:
                    printWatchList(trader.userWatchList());
                    break;
                case 4:
                    addToWatchListForm();
                    break;
            }
        }while(menuLogin.getOpcao()!=0);
        trader.fechaSessao();
    } 
    
    /**
     * Constroi a tabela para apresentar a watchlist do utilizador.
     * Pede os dados atuais a partir da API e os dados da watchlist do utilizador.
     * @param markets Empresas que o utilizador tem na watchlist
     */
    public void printWatchList(ArrayList<String> markets){
        ArrayList<Stock> stocks = new ArrayList<>();
        Date date = new java.util.Date();

        markets.forEach((s) -> {
            stocks.add(StockFetcher.getStock(s));
        });
        System.out.println("\n"+ date );
        System.out.printf("\n%-20s %-20s %-20s %-20s\n","NAME","SELL PRICE($)","BUY PRICE($)","VOLUME");
        stocks.forEach((company) -> {
            System.out.printf("%-20s %-20s %-20s %-20s\n",company.getName(),company.getPriceAsk(),company.getPriceBid(),company.getVolume());
        });
        System.out.println("\n");
    
    }
    /**
     * Formulario para inserir uma empresa à watchList do utilizador logado.
     */
    public void addToWatchListForm(){
        Utils.printLogo();
        Scanner scan = new Scanner(System.in);
        String company;
        System.out.print("Company: ");
        company=scan.next();
        trader.addToWatchList(company);
    }
    
    /**
	 * Formulario para inserir um novo CFD ao utilizador logado.
     * @throws SaldoException, CompanyNotFoundException
     */
    public void tradeForm() throws SaldoException, CompanyNotFoundException{
        CFD invest = null;
        String company=null;
        Scanner scin = new Scanner(System.in);
        menuTradeType.executa();
        if (menuTradeType.getOpcao() != 0) {
            String unitsS,stop_lossS, take_profitS;
            System.out.print("Company: ");
            company = scin.nextLine();
            System.out.print("Units: ");
            unitsS = scin.nextLine();
            int units = Integer.parseInt(unitsS);
            System.out.print("Stop Loss (Price): ");
            stop_lossS = scin.nextLine();
            double stop_loss = Double.parseDouble(stop_lossS);
            System.out.print("Take Profit (Price): ");
            take_profitS = scin.nextLine();
            double take_profit = Double.parseDouble(take_profitS);
            Stock stock = StockFetcher.getStock(company);
            switch (menuTradeType.getOpcao()) {
                case 1: invest = new CFD(company,stock.getPriceAsk(),CFDtype.Sell,units,stop_loss,take_profit);
                        break;
                case 2: invest = new CFD(company,stock.getPriceBid(),CFDtype.Buy,units,stop_loss,take_profit);
                        break;
            }
        }
       try{
           trader.addCFD(invest);
           trader.addToWatchList(company);
        }
       catch(SaldoException e){
          System.out.println(e.getMessage() + "\n");
          trader.addToWatchList(company);
       }
       catch(CompanyNotFoundException e){
           System.out.println(e.getMessage() + "\n");
       }
    }
    
    /**
	 * Imprime os CFD do utilizador atual.
     */
    public void printCFDs(){
        ArrayList<CFD> cfds = new ArrayList<>(trader.userPortfolio());
        int count=1;
        Stock nrate;
        
        System.out.printf("\n%-20s %-20s %-20s %-20s %-20s %-20s\n","ID","COMPANY","OPEN RATE","ATUAL RATE","TYPE","UNITS");
        for(CFD c: cfds){
            nrate = StockFetcher.getStock(c.getCompany());
            if(c.getType() == CFDtype.Buy){
                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",count++,c.getCompany(),c.getRate(),nrate.getPriceBid(),c.getType(),c.getUnits());
            }
            else{
                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",count++,c.getCompany(),c.getRate(),nrate.getPriceAsk(),c.getType(),c.getUnits());
            }
        }
        System.out.println("\n");
    
    }
    
    /**
	 * Formulario para fechar um CFD ao utilizador logado.
     * @throws Exception
     */
    public void closeTradeForm() throws Exception{
        String cfd;
        ArrayList<CFD> cfds = new ArrayList<>(trader.userPortfolio());
        Scanner scin = new Scanner(System.in);    
        printCFDs();
        System.out.printf("CFD number (0 to quit): ");
        cfd = scin.nextLine();
        int number = Integer.parseInt(cfd);
        if(number==0){}
        else{
            try{
                trader.removeCFD(number-1);
            }
            catch(Exception e){
                System.out.println("Not possible to execute.\n");
            }
        }
    }
    
    private void carregarMenus() {
        String[] main={"Login","Register"};
        String[] login={"Trade","Close Trade","WatchList lookup","Add company to watchlist"};
        String[] tradeType={"Sell","Buy"};
        menumain = new Menu(main);
        menuLogin = new Menu(login);
        menuTradeType = new Menu(tradeType);
    }
    
    private void initApp(){
        try {
            trader = (TraderInterface) SerializationUtil.deserialize("trader.db");
        }
        catch (IOException e) {
            trader = new Trader();
            System.out.println("Database not found.");
        }
        catch (ClassNotFoundException e) {
           trader = new Trader();
            System.out.println("Can't find database.");
        }
        catch (ClassCastException e) {
           trader = new Trader();
            System.out.println("Can't read database.");
        }
    }
    
    /**
	 * Formulario para login do utilizador.
     */
    public void signIn(){
        Scanner scan=new Scanner(System.in);
        String email,password;
        System.out.print("Email: ");
        email=scan.next();
        System.out.print("\n");
        System.out.print("Password: ");
        password=scan.next();
        try{
           trader.iniciaSessao(email,password);
           System.out.println("\nUser successfully logged in!");
        }
        catch(SemAutorizacaoException e){
            System.out.println(e.getMessage());
        }
        if(trader.isLogged()){
            try{
                menuLogin();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    /**
	 * Formulario para registar um utilizador.
	 * @throws UtilizadorExistenteException
     */
    public void signUp() throws UtilizadorExistenteException {
        User u=null;
        Scanner scin = new Scanner(System.in);
        String email,nome,password;
        System.out.print("Email: ");
        email = scin.nextLine();
        System.out.print("Name: ");
        nome = scin.nextLine();
        System.out.print("Password: ");
        password = scin.nextLine();
        u = new User(nome,email,password);
        try{
            trader.registarUtilizador(u);
            System.out.println("\nYou have successfully registered!");
        }
        catch(UtilizadorExistenteException e){
            System.out.println(e.getMessage());
        }
    }
}