/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracleBD;

/**
 *
 * @author zmlef
 */
public class Users {
 
    int user_id;
    String username;
    String default_tablespace;
    String temporary_tablespace;
    String tablespace_name;
    int quota_bytes;
    String account_status;
    String privilege;
    String sid;
    String serial;
    String active;
    String cpu_usage;
    
    public Users() {
        this.user_id = 0;
        this.username = null;
        this.default_tablespace = null;
        this.temporary_tablespace = null;
        this.account_status = null;
        this.tablespace_name = null;
        this.quota_bytes = 0;
        this.sid = null;
        this.serial = null;
        this.active = "NOT ACTIVE";
        this.cpu_usage = null;
        this.privilege = null;
    }
    public Users(int id, String u, String dt, String tt,String as){
        this.user_id = id;
        this.username=u;
        this.default_tablespace = dt;
        this.temporary_tablespace = tt;
        this.account_status = as;
        
    }
    
    public int getUser_id(){return this.user_id;}
    public String getUser(){return this.username;}
    public String getDefTab(){return this.default_tablespace;}
    public String getTempTab(){return this.temporary_tablespace;}
    public String getTabName(){return this.tablespace_name;}
    public String getAccStat(){return this.account_status;}
    public String getPriv(){return this.privilege;}
    public int getQb() { return this.quota_bytes;}
    public String getCPU() { return this.cpu_usage; }
    public void setSid(String s){ this.sid = s; }
    public void setSerial(String se){ this.serial = se; }
    public void setAct(String a){ this.active = a; }
    public void setCPU(String cpu){ this.cpu_usage = cpu; }
    public void setPriv(String priv){ this.privilege = priv;}
    public void setTabName(String tab){this.tablespace_name = tab;}
    public void setQb(int qb){this.quota_bytes = qb;}
}
 
    

