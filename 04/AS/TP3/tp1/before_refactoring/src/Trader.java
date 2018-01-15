/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trader;

import java.io.*;
import java.util.*;

/**
 *
 * @author rgllm
 */
public class Trader implements Serializable{
    
    private boolean logged = false;
    private User loggedUser; //nao modificar, get apenas do email
    private Map<String,User> allUsers = new TreeMap<>();
    
    public Trader(){
        allUsers = new TreeMap<>();
    }
    
     public Trader(Trader trader){
        this.logged=trader.isLogged();
        this.loggedUser=trader.getloggedUser().clone();
        this.allUsers=new TreeMap(trader.getUsers());
    }
     
    public boolean isLogged() {return logged;}
    public User getloggedUser() {return loggedUser;}
    public Map<String,User> getUsers(){
        Map<String,User> res = new TreeMap<>();
        allUsers.entrySet().forEach((u) -> {
            res.put(u.getValue().getEmail(), (User) u.getValue().clone());
        });
        return res;
    }
  
    public void setLogado(boolean logged) {this.logged = logged;}
    public void setUserAtual(User loggedUser) {this.loggedUser = loggedUser;}
    public void setUsers(Map<String,User> newUsers){
        allUsers.clear();
        for(Map.Entry<String,User> u:newUsers.entrySet())
            allUsers.put(u.getKey(),u.getValue().clone());
    }
    
    public boolean existeUtilizador (User u){

        if(allUsers.containsKey(u.getEmail())) return true;
        else return false;
    }
    
    public void registarUtilizador (User u) throws UtilizadorExistenteException{

        if(existeUtilizador(u)==true){
            throw new UtilizadorExistenteException("Email already registered.");
        }
        else{
            allUsers.put(u.getEmail(),u.clone());
        }
    }
    
    public void iniciaSessao (String email , String password ) throws SemAutorizacaoException {
        
        if(!allUsers.containsKey(email)){
            throw new SemAutorizacaoException("Email not found.");
        }
        else if(!allUsers.get(email).getPassword().equals(password)){
            throw new SemAutorizacaoException("Incorrect password.");
        }
        else{
            logged=true;
            loggedUser=allUsers.get(email);
        }
    }
    
    public void fechaSessao (){
        logged=false;
        loggedUser=null;
    }
    
    public void addToWatchList(String company){
       String loggedEmail = loggedUser.getEmail();
       if(!allUsers.get(loggedEmail).getWatchList().contains(company)){
        allUsers.get(loggedEmail).getWatchList().add(company);
       }
    }
    
    public void addCFD(CFD invest) throws SaldoException, CompanyNotFoundException{
        double montante = invest.getRate() * invest.getUnits();
        String loggedEmail = loggedUser.getEmail();
       if(StockFetcher.getStock(invest.getCompany()).getVolume()==0.0){
            throw new CompanyNotFoundException("\nCompany not found.");
        }
        if(allUsers.get(loggedEmail).getSaldo() < montante){
            throw new SaldoException("\nInsufficient funds.");
        }
        else{
            allUsers.get(loggedEmail).getPortfolio().add(invest);
            double saldoAtual = allUsers.get(loggedEmail).getSaldo();
            allUsers.get(loggedEmail).setSaldo(saldoAtual-montante);
        }  
    }
    
    public void removeCFD(int pos) throws Exception{
        String loggedEmail = loggedUser.getEmail();
        CFD toRemove = (CFD) allUsers.get(loggedEmail).getPortfolio().get(pos);
        double atualPrice=0.0;
        double novoSaldo=0.0;
        if(toRemove.getType() == CFDtype.Buy){
            atualPrice = StockFetcher.getStock(toRemove.getCompany()).getPriceAsk();
            double difference = (atualPrice * toRemove.getUnits()) - (toRemove.getRate() * toRemove.getUnits());
            novoSaldo = allUsers.get(loggedEmail).getSaldo() + (toRemove.getRate()*toRemove.getUnits() - difference);
        }
        else{
            atualPrice = StockFetcher.getStock(toRemove.getCompany()).getPriceBid();
            double difference = (atualPrice * toRemove.getUnits()) - (toRemove.getRate() * toRemove.getUnits());
            novoSaldo = allUsers.get(loggedEmail).getSaldo() + (toRemove.getRate()*toRemove.getUnits() + difference);
        }
        try{
            allUsers.get(loggedEmail).getPortfolio().remove(pos);
            allUsers.get(loggedEmail).setSaldo(novoSaldo);
        }
        catch(Exception e){
            throw new Exception("Not possible to close trade");
        }
    }
    
    public ArrayList<CFD> userPortfolio(){
        String loggedEmail = loggedUser.getEmail();
        return (allUsers.get(loggedEmail).getPortfolio());
    }
    
    public ArrayList<String> userWatchList(){
        String loggedEmail = loggedUser.getEmail();
        return (allUsers.get(loggedEmail).getWatchList());
    }
    
    public double userSaldo(){
        String loggedEmail = loggedUser.getEmail();
        return(allUsers.get(loggedEmail).getSaldo());
    }

    public void atualizaCFD(CFD c){
                                Stock novo = StockFetcher.getStock(c.getCompany());
                        if(c.getType() == CFDtype.Buy){
                            double actualValue = novo.getPriceAsk();
                            if (actualValue <= c.getStop_loss() || actualValue >= c.getTake_profit()) {
                                String email = u.getKey();
                                double difference = (actualValue * c.getUnits()) - (c.getRate() * c.getUnits());
                                double novoSaldo = allUsers.get(email).getSaldo() + (c.getRate()*c.getUnits() - difference);
                                try{
                                    it.remove(c);
                                    allUsers.get(email).setSaldo(novoSaldo);
                                }
                                catch(Exception e){
                            }
                        }
                        }
                        else{
                            double actualValue = novo.getPriceBid();
                            if (actualValue >= c.getStop_loss() || actualValue <= c.getTake_profit()) {
                                String email = u.getKey();
                                double difference = (actualValue * c.getUnits()) - (c.getRate() * c.getUnits());
                                double novoSaldo = allUsers.get(email).getSaldo() + (c.getRate()*c.getUnits() + difference);
                                try{
                                    it.remove(c);
                                    allUsers.get(email).setSaldo(novoSaldo);
                                }
                                catch(Exception e){
                                }
                            }
                        }
    }

    public void atualizaUsers(){
     allUsers.entrySet().forEach((u) -> {
                    ArrayList<CFD> it = u.getValue().getPortfolio();
                    it.forEach((c) -> {
                        atualizaCFD(c);
                    });
                });           
    }
    
    public void portfolioUpdater(){
        Timer t = new Timer( );
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                atualizaUsers();
            }
    }, 5000,200000);
   }
}