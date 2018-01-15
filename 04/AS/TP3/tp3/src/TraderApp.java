package com.rgllm.trader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;


public class TraderApp{

    private static Trader trader;
    private static Menu menumain,menuLogin,menuTradeType;
    
    public static void main(String[] args) throws ClassNotFoundException, UtilizadorExistenteException, IOException, Exception{
        TraderApp main = new TraderApp();
        main.first();
    }
    
    public void first() throws ClassNotFoundException, UtilizadorExistenteException, IOException, Exception {
        carregarMenus();
        trader = new Trader();
        
        Logger mongoLogger = Logger.getLogger("org.mongodb.driver");
        mongoLogger.setLevel(Level.SEVERE); 
        org.apache.log4j.Logger.getRootLogger().setLevel(org.apache.log4j.Level.OFF);
        
        Thread updater = new Thread() {
            @Override
            public void run() {
                trader.portfolioUpdater();
            }
        };
        updater.start();
        Thread notifications = new Thread() {
            @Override
            public void run() {
                trader.notificationUpdater();
            }
        };
       notifications.start();
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
        System.out.println("See you soon!");
        while(updater.isAlive()){updater.interrupt();}
        while(notifications.isAlive()){notifications.interrupt();}
    } 
    

    public void menuLogin() throws Exception{
        Utils.printLogo();
        System.out.println("Balance: " + trader.userSaldo() + "$" + "\n");
        Scanner scan=new Scanner(System.in);
        printUserNotifications();
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
                case 5:
                    addAssetForm();
                    break;
            }
        }while(menuLogin.getOpcao()!=0);
        trader.fechaSessao();
    }
    
    public void printWatchList(ArrayList<String> markets){
        ArrayList<Stock> stocks = new ArrayList<>();
        printUserNotifications();
        Date date = new java.util.Date();

        markets.forEach((s) -> {  
            try {
                stocks.add(YahooFinance.get(s));
            } catch (IOException ex) {
                Logger.getLogger(TraderApp.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        System.out.println("\n"+ date );
        System.out.printf("\n%-20s %-20s %-20s %-20s\n","NAME","SELL PRICE($)","BUY PRICE($)","VOLUME");
        stocks.forEach((company) -> {
            System.out.printf("%-20s %-20s %-20s %-20s\n",company.getName(),company.getQuote().getAsk(),company.getQuote().getBid(),company.getQuote().getVolume());
        });
        System.out.println("\n");
    
    }
    
    public void printUserNotifications(){
        System.out.println("Notifications:");
        ArrayList<Asset> add = trader.getUserNotifications();
        System.out.println("\n");
        if(!add.isEmpty()){
            add.forEach((a) -> {
                System.out.println("Company " + a.getCompany() + " as reached the price " + a.getEspec_price() + ".");
            });
        }
        System.out.print("\n");
       }
    
    public void addToWatchListForm(){
        Utils.printLogo();
        printUserNotifications();
        Scanner scan = new Scanner(System.in);
        String company;
        System.out.print("Company: ");
        company=scan.next();
        try {
            trader.addToWatchList(company);
        } catch (IOException ex) {
           System.out.println("\nCompany not found.");
        }
    }
    
    public void addAssetForm(){
        Utils.printLogo();
        printUserNotifications();
        Scanner scan = new Scanner(System.in);
        System.out.print("Company: ");
        String company=scan.nextLine();
        System.out.print("Espected Price: ");
        double price = Double.parseDouble(scan.nextLine());
        Asset toAdd = new Asset(trader.getloggedUser().getEmail(),company,price);
        trader.insertNotification(toAdd);
    }
    
    public void tradeForm() throws SaldoException, CompanyNotFoundException, IOException{
        CFD invest = null;
        String company=null;
        Scanner scin = new Scanner(System.in);
        printUserNotifications();
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
            Stock stock = YahooFinance.get(company);
            switch (menuTradeType.getOpcao()) {
                case 1: invest = new CFD(company,stock.getQuote().getAsk().doubleValue(),CFDtype.Sell,units,stop_loss,take_profit);
                        break;
                case 2: invest = new CFD(company,stock.getQuote().getBid().doubleValue(),CFDtype.Buy,units,stop_loss,take_profit);
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
    }
    
    public void printCFDs() throws IOException{
        ArrayList<CFD> cfds = new ArrayList<>(trader.userPortfolio());
        int count=1;
        Stock nrate;
        printUserNotifications();
        System.out.printf("\n%-20s %-20s %-20s %-20s %-20s %-20s\n","ID","COMPANY","OPEN RATE","ATUAL RATE","TYPE","UNITS");
        for(CFD c: cfds){
            nrate = YahooFinance.get(c.getCompany());
            if(c.getType() == CFDtype.Buy){
                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",count++,c.getCompany(),c.getRate(),nrate.getQuote().getBid(),c.getType(),c.getUnits());
            }
            else{
                System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",count++,c.getCompany(),c.getRate(),nrate.getQuote().getAsk(),c.getType(),c.getUnits());
            }
        }
        System.out.println("\n");
    
    }
    
    public void closeTradeForm() throws Exception{
        printUserNotifications();
        String cfd;
        ArrayList<CFD> cfds = new ArrayList<>(trader.userPortfolio());
        Scanner scin = new Scanner(System.in);    
        printCFDs();
        System.out.printf("CFD number: ");
        cfd = scin.nextLine();
        int number = Integer.parseInt(cfd);
        trader.removeCFD(number-1);
    }
    
    private void carregarMenus() {
        String[] main={"Login","Register"};
        String[] login={"Trade","Close Trade","WatchList lookup","Add company to watchlist","Add Notification"};
        String[] tradeType={"Sell","Buy"};
        menumain = new Menu(main);
        menuLogin = new Menu(login);
        menuTradeType = new Menu(tradeType);
    }

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
        u = new User(email,nome,password);
        trader.registarUtilizador(u);
        System.out.println("\nYou have successfully registered!");
    }
}