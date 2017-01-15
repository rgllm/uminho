package billsplitter;
import visual.*;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BillSplitter {
    DAOUtilizador daoU;
    
    public BillSplitter(){
        daoU= new DAOUtilizador();
    }

    public boolean login(String email, String password){
        Utilizador u=daoU.get(email);
        
        if(u!= null && u.getPassword().equals(password)) return true;
        return false;
    }
    
    public Utilizador getUser(String email){
        DAOUtilizador daoU=new DAOUtilizador();
        return daoU.get(email);
        
    }
    
    public Casa getCasa(int idcasa){
        DAOCasa daoC=new DAOCasa();
        return daoC.get(idcasa);
    }
    /*
    public boolean registo(String nome,String email,String password,String morada, String codpostal, String localidade, String telefone, String observacoes){
      if(daoU.get(email)==null) return false;
      // verificar campos
      //registar casa
      // registar user
    }
    */
}
