package billsplitter;

import java.sql.*;
import java.util.*;

public class DAOCasa {

    public void insert(Casa c){
        Connection con=null;
        String morada=c.getMorada();
        String cpostal=c.getCpostal();
        String localidade=c.getLocalidade();
        String telefone=c.getTelefone();
        String observacoes=c.getObservacoes();
        try{
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();
            int ret=st.executeUpdate("INSERT INTO `billsplitter`.`casa` (`morada`,`cod_postal`,`localidade`,`telefone`,`observacoes`)"
                    + "                                          VALUES('"+morada+"', '"+cpostal+"', '"+localidade+"', '"+telefone+"', '"+observacoes+"');");
        }
        catch(Exception e){ e.printStackTrace();}
        finally {
            try {
            con.close();
            } catch (Exception e) {
            e.printStackTrace();
            }
        }
    }

    public int lastId(){
        Connection con=null;
        try{
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT `idcasa` FROM `casa` ORDER BY idcasa DESC LIMIT 1");
            if(rs.next())
                return rs.getInt("idcasa");
            return -1;
        }
        catch(Exception e){ e.printStackTrace();}
        finally {
            try {
            con.close();
            
            } catch (Exception e) {
            e.printStackTrace();
            }
        }
        return -1;
    }

    public Casa get(Object key){
        Connection con=null;
        Casa nova=null;
        try{
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();
            int chave=Integer.parseInt(key.toString());
            ResultSet rs=st.executeQuery("SELECT * FROM `billsplitter`.`casa` WHERE `idcasa`="+chave+";");
            if(rs.next()){
                int id= rs.getInt("idcasa");
                String morada=rs.getString("morada");
                String cpostal=rs.getString("cod_postal");
                String localidade=rs.getString("localidade");
                String telefone=rs.getString("telefone");
                String observacoes=rs.getString("observacoes");
                nova= new Casa(id, morada, cpostal, localidade, telefone, observacoes);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    return nova;
    }


    /*public void update(Utilizador u){}
      public void delete(Casa c){}*/
}

