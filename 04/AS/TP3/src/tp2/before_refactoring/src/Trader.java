package com.rgllm.trader;


import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;


public class Trader implements Serializable{
    
    private boolean logged = false;
    private User loggedUser;
    
    public Trader(){
        loggedUser=null;
    }
    
    public Trader(Trader trader){
        this.logged=trader.isLogged();
        this.loggedUser=trader.getloggedUser().clone();
    }
     
    public boolean isLogged() {return logged;}
    public User getloggedUser() {return loggedUser;}
    public void setLogado(boolean logged) {this.logged = logged;}
    public void setUserAtual(User loggedUser) {this.loggedUser = loggedUser;}

    public boolean existeUtilizador (User u){
        return userDAO.existeUser(u);
    }
    
    public void registarUtilizador (User u) throws UtilizadorExistenteException{

        if(existeUtilizador(u)==true){
            throw new UtilizadorExistenteException("Email already registered.");
        }
        else{
            userDAO.inserirUser(u);
        }
    }
    
    public void iniciaSessao (String email , String password ) throws SemAutorizacaoException {      
        if(!userDAO.loginUser(email,password)){
            throw new SemAutorizacaoException("The user does not exist or the password is incorret.");
        }
        else{
            logged=true;
            loggedUser=userDAO.devolveUser(email);
        }
    }
    
    public void fechaSessao (){
        logged=false;
        loggedUser=null; 
    }
    
    public void addToWatchList(String company) throws IOException, CompanyNotFoundException{
       String loggedEmail = loggedUser.getEmail();
       if(YahooFinance.get(company).isValid()){
        loggedUser.getWatchList().add(company);
        userDAO.addToWatchlist(loggedEmail, company);
       }
       else{
           throw new CompanyNotFoundException("\nCompany not found.");
       }
    }
    
    public void addCFD(CFD invest) throws SaldoException, CompanyNotFoundException, IOException{
        double montante = invest.getRate() * invest.getUnits();
        String loggedEmail = loggedUser.getEmail();
        double saldoAtual = loggedUser.getSaldo();
       if(YahooFinance.get(invest.getCompany()).getQuote().getVolume()==0.0){
            throw new CompanyNotFoundException("\nCompany not found.");
        }
        if(saldoAtual < montante){
            throw new SaldoException("\nInsufficient funds.");
        }
        else{
            userDAO.addCFD(loggedEmail, invest);
            loggedUser.getPortfolio().add(invest);
            userDAO.atualizarSaldo(loggedEmail, saldoAtual-montante);
            loggedUser.setSaldo(saldoAtual-montante);
        }  
    }
    
    public void removeCFD(int pos) throws Exception{
        String loggedEmail = loggedUser.getEmail();
        CFD toRemove = (CFD) loggedUser.getPortfolio().get(pos);
        double atualPrice=0.0;
        double novoSaldo=0.0;
        if(toRemove.getType() == CFDtype.Buy){
            atualPrice = YahooFinance.get(toRemove.getCompany()).getQuote().getAsk().doubleValue();
            double difference = (atualPrice * toRemove.getUnits()) - (toRemove.getRate() * toRemove.getUnits());
            novoSaldo = loggedUser.getSaldo() + (toRemove.getRate()*toRemove.getUnits() - difference);
        }
        else{
            atualPrice = YahooFinance.get(toRemove.getCompany()).getQuote().getBid().doubleValue();
            double difference = (atualPrice * toRemove.getUnits()) - (toRemove.getRate() * toRemove.getUnits());
            novoSaldo = loggedUser.getSaldo() + (toRemove.getRate()*toRemove.getUnits() + difference);
        }
        try{
            userDAO.removeCFD(loggedEmail, toRemove);
            loggedUser.getPortfolio().remove(toRemove);
            userDAO.atualizarSaldo(loggedEmail, novoSaldo);
            loggedUser.setSaldo(novoSaldo);
        }
        catch(Exception e){
            throw new Exception("Not possible to close trade");
        }
    }
    
    public ArrayList<CFD> userPortfolio(){
        return (loggedUser.getPortfolio());
    }
    
    public ArrayList<String> userWatchList(){
        return (loggedUser.getWatchList());
    }
    
    public double userSaldo(){
        return(loggedUser.getSaldo());
    }
    
    public void insertNotification(Asset asset){
        notificationsDAO.addNotification(asset);
    }
    
    public ArrayList<Asset> getUserNotifications(){
        ArrayList<Asset> notifications = notificationsDAO.getNotifications();
        ArrayList<Asset> rt = new ArrayList<>();
        notifications.stream().filter((a) -> (a.getEmail().equals(loggedUser.getEmail()) && a.getState() == true)).map((a) -> {
            rt.add(a);
            return a;
        }).forEachOrdered((a) -> {
            notificationsDAO.removeNotification(a);
        });
        return rt;
    } 

    public synchronized void notificationUpdater(){
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run(){
                ArrayList<Asset> notifications = new ArrayList(notificationsDAO.getNotifications());
                notifications.forEach((a) -> {
                    double atual_price=-1;
                    try {
                        atual_price = YahooFinance.get(a.getCompany()).getQuote().getPrice().doubleValue();
                    } catch (IOException ex) {
                        Logger.getLogger(Trader.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    if (a.getEspec_price()==atual_price) {
                        a.setState(true);
                        notificationsDAO.changeNotificationState(a);
                    }
                });
            }
        }, 0,1000);
    }
    
    
    public synchronized void  portfolioUpdater(){
        Timer t = new Timer( );
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                userDAO.getAllUsers().entrySet().forEach((u) -> {
                    ArrayList<CFD> it = u.getValue().getPortfolio();
                    it.forEach((c) -> {
                        Stock novo = null;
                        try {
                            novo = YahooFinance.get(c.getCompany());
                        } catch (IOException ex) {
                            Logger.getLogger(Trader.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        if(c.getType() == CFDtype.Buy){
                            double actualValue = novo.getQuote().getAsk().doubleValue();
                            if (actualValue <= c.getStop_loss() || actualValue >= c.getTake_profit()) {
                                String email = u.getKey();
                                double difference = (actualValue * c.getUnits()) - (c.getRate() * c.getUnits());
                                double novoSaldo = userDAO.devolveUser(email).getSaldo() + (c.getRate()*c.getUnits() - difference);
                                try{
                                    userDAO.removeCFD(loggedUser.getEmail(), c);
                                    loggedUser.getPortfolio().remove(c);
                                    userDAO.atualizarSaldo(email, novoSaldo);
                                    loggedUser.setSaldo(novoSaldo);
                                }
                                catch(Exception e){
                            }
                        }
                        }
                        else{
                            double actualValue = novo.getQuote().getBid().doubleValue();
                            if (actualValue >= c.getStop_loss() || actualValue <= c.getTake_profit()) {
                                String email = u.getKey();
                                double difference = (actualValue * c.getUnits()) - (c.getRate() * c.getUnits());
                                double novoSaldo = userDAO.devolveUser(email).getSaldo() + (c.getRate()*c.getUnits() + difference);
                                try{
                                    it.remove(c);
                                    loggedUser.getPortfolio().remove(c);
                                    userDAO.atualizarSaldo(email, novoSaldo);
                                    loggedUser.setSaldo(novoSaldo);
                                }
                                catch(Exception e){
                                }
                            }
                        }
                    });
                });        
            }
    }, 0,500000);
   }
}