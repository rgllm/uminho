package trader;

import java.io.*;
import java.util.*;

public class Trader implements Serializable, TraderInterface{
    
    private User loggedUser;
    private Map<String,User> allUsers = new TreeMap<>();
    
    public Trader(){
        allUsers = new TreeMap<>();
    }
    
     public Trader(TraderInterface trader){
        this.loggedUser=trader.getloggedUser().clone();
        this.allUsers=new TreeMap(trader.getUsers());
    }
     
    
	public boolean isLogged() {return (loggedUser==null);}

    
	public User getloggedUser() {return loggedUser;}

    
	public Map<String,User> getUsers(){
        Map<String,User> res = new TreeMap<>();
        allUsers.entrySet().forEach((u) -> {
            res.put(u.getValue().getEmail(), (User) u.getValue().clone());
        });
        return res;
    }

	public void setUserAtual(User loggedUser) {this.loggedUser = loggedUser;}

	public void setUsers(Map<String,User> newUsers){
        allUsers.clear();
        for(Map.Entry<String,User> u:newUsers.entrySet())
            allUsers.put(u.getKey(),u.getValue().clone());
    }
    
    /**
     * Método que verifica se existe um utilizador.
     * @param u
     * @return 
     */
	public boolean existeUtilizador (User u){

        if(allUsers.containsKey(u.getEmail())) return true;
        else return false;
    }
    
    /**
     * Método que regista um novo utilizador caso este não exista.
     * @param args u
     * @throws UtilizadorExistenteException
     */
	public void registarUtilizador (User u) throws UtilizadorExistenteException{

        if(existeUtilizador(u)==true){
            throw new UtilizadorExistenteException("Email already registered.");
        }
        else{
            allUsers.put(u.getEmail(),u.clone());
        }
    }
    
        /**
     * Método que inicia a sessão ao utilizador, verificando primeiro se o email e password coincidem.
     * @param email
     * @param password
     * @throws SemAutorizacaoException 
     */
	public void iniciaSessao (String email , String password ) throws SemAutorizacaoException {
        
        if(!allUsers.containsKey(email)){
            throw new SemAutorizacaoException("Email not found.");
        }
        else if(!allUsers.get(email).getPassword().equals(password)){
            throw new SemAutorizacaoException("Incorrect password.");
        }
        else{
            loggedUser=allUsers.get(email);
        }
    }
    
        /**
     * Método para sair da sessão atual do utilizador.
     */
	public void fechaSessao (){
        loggedUser=null;
    }
    
        /**
     * Adiciona uma nova empresa à watchlist do utilizador logado.
     * Valida em primeiro lugar se a empresa pretendida existe.
     * @param company
     * @throws IOException
     * @throws CompanyNotFoundException 
     */
	public void addToWatchList(String company){
       String loggedEmail = loggedUser.getEmail();
       if(!allUsers.get(loggedEmail).getWatchList().contains(company)){
        allUsers.get(loggedEmail).getWatchList().add(company);
       }
    }
    
        /**
     * Adiciona um determinado CFD recebido como parâmetro.
     * Verifica se o saldo do utilizador logado permite o investimento.
     * Verifica se a empresa do CFD existe;
     * Remove do saldo do utilizador logado o valor do investimento.
     * @param invest
     * @throws SaldoException
     * @throws CompanyNotFoundException
     * @throws IOException 
     */
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
    
        /**
     * Venda de um CFD
     * Caso seja do tipo Buy a fórmula de cálculo é diferente de Sell
     * Adiciona o valor da venda ao saldo do utilizador logado.
     * @param pos
     * @throws Exception 
     */
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
    
    /**
     * Verifica o estado dos CFD e atualiza o seu estado.
     */
	public void portfolioUpdater(){
        Timer t = new Timer( );
        t.scheduleAtFixedRate(new TimerTask() {
            
            public void run() {
                allUsers.entrySet().forEach((u) -> {
                    ArrayList<CFD> it = u.getValue().getPortfolio();
                    it.forEach((c) -> {
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
                    });
                });        
            }
    }, 5000,200000);
   }
}