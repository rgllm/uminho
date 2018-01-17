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
public class Sessions {
    
    int session_id;
    Users u;
    String schemaName;
    String login_time;
    
    public Sessions(){
        this.session_id = 0;
        this.u = new Users();
        this.schemaName = null;
        this.login_time = null;
    }
    public Sessions(int id, Users u , String sn, String lt){
        this.session_id = id;
        this.u = u;
        this.schemaName = sn;
        this.login_time = lt;
    }
    
    public int getSID(){return this.session_id;}
    public Users getUsers(){ return this.u;}
    public String getSn(){return this.schemaName;}
    public String getLt(){return this.login_time;}
    
}
