package com.rgllm.trader;


public class Asset {
    
    private String email;
    private String company;
    private double espec_price;
    private boolean state;

    public Asset(String email, String company, double espec_price) {
        this.email = email;
        this.company = company;
        this.espec_price = espec_price;
        this.state = false;
    }
    
    public Asset(String email, String company, double espec_price, boolean state) {
        this.email = email;
        this.company = company;
        this.espec_price = espec_price;
        this.state = state;
    }

    public String getEmail() {
        return email;
    }

    public String getCompany() {
        return company;
    }

    public double getEspec_price() {
        return espec_price;
    }

    public Boolean getState() {
        return state;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setEspec_price(double espec_price) {
        this.espec_price = espec_price;
    }

    public void setState(Boolean state) {
        this.state = state;
    }
   
}
