package com.rgllm.trader;

import java.io.Serializable;
import java.util.Objects;
import java.util.Date;

public class CFD implements Serializable {
  
    private final Date time;
    private final String company;
    private final double rate;
    private final CFDtype type;
    private final int units;
    private final double stop_loss;
    private final double take_profit;

    public CFD(String company, double rate, CFDtype type, int units, double stop_loss, double take_profit) {
        this.time=new Date();
        this.company = company;
        this.rate = rate;
        this.type = type;
        this.units = units;
        this.stop_loss = stop_loss;
        this.take_profit = take_profit;
    }
    
    public CFD(String time, String company, double rate, CFDtype type, int units, double stop_loss, double take_profit) {
        this.time=new Date();
        this.company = company;
        this.rate = rate;
        this.type = type;
        this.units = units;
        this.stop_loss = stop_loss;
        this.take_profit = take_profit;
    }
    
    public Date getTime(){
        return time;
    }

    public String getCompany(){
        return company;
    }
    
    public double getRate() {
        return rate;
    }

    public CFDtype getType() {
        return type;
    }

    public int getUnits() {
        return units;
    }

    public double getStop_loss() {
        return stop_loss;
    }

    public double getTake_profit() {
        return take_profit;
    }

    @Override
    public String toString() {
        return "CFD{" + "time=" + time.toString() + "company=" + company + ", rate=" + rate + ", type=" + type + ", units=" + units + ", stop_loss=" + stop_loss + ", take_profit=" + take_profit + '}';
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
        final CFD other = (CFD) obj;
        if (this.units != other.units) {
            return false;
        }
        if (!Objects.equals(this.company, other.company)) {
            return false;
        }
        if (!Objects.equals(this.rate, other.rate)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.stop_loss, other.stop_loss)) {
            return false;
        }
        if (!Objects.equals(this.take_profit, other.take_profit)) {
            return false;
        }
        return true;
    }
}
