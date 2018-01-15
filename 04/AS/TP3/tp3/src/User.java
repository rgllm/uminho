package com.rgllm.trader;

import java.io.*;
import java.util.Objects;
import java.util.ArrayList;

public class User implements Comparable<User>, Serializable {
    
    private final String email;
    private final String nome;
    private final String password;
    private Double saldo;
    private ArrayList<String> watchList = new ArrayList();
    private ArrayList<CFD> portfolio = new ArrayList();

    public User(String email, String nome, String password, Double saldo, ArrayList list, ArrayList port) {
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.saldo = saldo;
        this.watchList= new ArrayList(list);
        this.portfolio = new ArrayList(port);
    }
    
    public User(String email, String nome, String password) {
        this.email = email;
        this.nome = nome;
        this.password = password;
        this.saldo = 10000.0;
        this.watchList = new ArrayList();
        this.portfolio = new ArrayList();
    }
    
    public User (User v){
        email=v.getEmail();
        nome=v.getNome();
        password=v.getPassword();
        saldo=v.getSaldo();
        watchList=new ArrayList(v.getWatchList());
        portfolio = new ArrayList(v.getPortfolio());
    }
    
    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getPassword() {
        return password;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }
    
    public ArrayList getWatchList(){
        return this.watchList;
    }
    
    public ArrayList getPortfolio(){
        return this.portfolio;
    }
    
    public void setWatchList(ArrayList watchList){
        this.watchList = new ArrayList(watchList);
    }
    
    public void setPortfolio(ArrayList portfolio){
        this.portfolio = new ArrayList(portfolio);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.email, other.email)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.password, other.password)) {
            return false;
        }
        return Objects.equals(this.saldo, other.saldo);
    }
    
    @Override
     public String toString() {
        return "Utilizador{" +
                "email=" + email +
                ", nome=" + nome +
                ", password=" +password +
                ", saldo=" + saldo +
                ", WatchList=" + watchList.toString() + 
                ", Portfolio=" + portfolio.toString() + '}';
    }

    @Override
    public User clone() {
        return new User(this);
    }

    @Override
    public int compareTo(User u){
        return this.getEmail().compareTo(u.getEmail());
    }
}
