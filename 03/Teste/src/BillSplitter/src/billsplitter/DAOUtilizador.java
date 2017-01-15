/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billsplitter;

import java.sql.*;
import java.util.*;

public class DAOUtilizador {

    public java.sql.Date convertDateToSQL(java.util.Date javaDate) {
        java.sql.Date sqlDate = null;
        if (javaDate != null) {
            sqlDate = new java.sql.Date(javaDate.getTime());
        }
        return sqlDate;
    }

    public java.util.Date convertDateToJava(java.sql.Date sqlDate) {
        java.util.Date javaDate = null;
        if (sqlDate != null) {
            javaDate = new java.util.Date(sqlDate.getTime());
        }
        return javaDate;
    }


    public void insert(Utilizador u){
        Connection con= null;
        String email=u.getEmail();
        String nome=u.getNome();
        java.util.Date chegada=u.getData_chegada();
        java.util.Date saida=u.getData_saida();
        String password=u.getPassword();
        int casa =u.getCasa();
        ArrayList<String> moradores = new ArrayList<>();
        try{
            con= new Ligacao().getConnection();
            Statement st=con.createStatement();
            System.out.println("casa : "+ u.getCasa());
            ResultSet rs= st.executeQuery("SELECT `email` FROM `user` WHERE `casa_idcasa`="+u.getCasa()+";");
            while(rs.next()){
                moradores.add(rs.getString("email"));
            }
            for(String morador: moradores)
                st.executeUpdate("INSERT INTO `situacao_irregular` (`email`, `valor`, `user_email`) "
                        + "VALUES('"+email+"',0,'"+ morador +"');");

            st.executeUpdate("INSERT INTO `user` (`email`, `nome`, `data_chegada`, `password`, `saldo`, `casa_idcasa`)"
                + "VALUES ('"+email +"', '"+nome +"', '"+convertDateToSQL(chegada) +"','"+password +"',0, '"+casa +"');");

            for(String morador : moradores)
                st.executeUpdate("INSERT INTO `situacao_irregular` (`email`, `valor`, `user_email`) "
                        + "VALUES('"+morador+"',0,'"+ email +"');");
        }catch(Exception e){ e.printStackTrace();}
        finally {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /*public void update(Utilizador u){
    }*/

    public void delete(Utilizador u){
        Connection con= null;
        String email=u.getEmail();
        try{
            con= new Ligacao().getConnection();
            Statement st=con.createStatement();
            int ret=st.executeUpdate("DELETE FROM `billsplitter`.`user` WHERE `email`='"+email+"';");

        }catch(Exception e){ e.printStackTrace();}
        finally {
            try {
            con.close();
            } catch (Exception e) {
            e.printStackTrace();
            }
        }
    }

    public void atualizarSaldo(String email){
        Connection con=null;
        float saldo=0;
        try{
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();

            ResultSet rs=st.executeQuery("SELECT `valor` FROM `situacao_irregular` WHERE user_email='"+email+"';");
            while(rs.next()){
                saldo+=rs.getFloat("valor");
            }
            int ret = st.executeUpdate("UPDATE `user` SET `saldo`="+saldo+" WHERE email='"+email+"';");
        }
        catch(Exception e){ e.printStackTrace();}
    }

    public Utilizador get(Object key) {
        Connection con=null;
        Utilizador novo= null;
        try {
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();
            String chave=key.toString();
            ResultSet rs=st.executeQuery("SELECT * FROM `user` WHERE email='"+chave+"';");
            if(rs.next()){
                String email=rs.getString("email");
                String nome=rs.getString("nome");
                java.util.Date data_chegada=convertDateToJava(rs.getDate("data_chegada"));
                java.util.Date data_saida=convertDateToJava(rs.getDate("data_saida"));
                String password=rs.getString("password");
                float saldo=rs.getFloat("saldo");
                int casa=rs.getInt("casa_idcasa");
                HashMap<String,Float> si=new HashMap<>();
                rs=st.executeQuery("SELECT * FROM `situacao_irregular` WHERE `user_email`='"+email+"';");
                while(rs.next()){
                    System.out.println(">");
                   si.put(rs.getString(1), rs.getFloat(2));
                }
                novo=new Utilizador(email,nome,data_chegada,data_saida,password,saldo,casa,si);
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
    return novo;
    }

    public ArrayList<String> getUtilizadoresCasa(int idcasa){
        ArrayList<String> todos=new ArrayList<>();
        Connection con=null;
        try{
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT `email` FROM `user` WHERE `casa_idcasa`="+idcasa+";");
            while(rs.next()){
                todos.add(rs.getString("email"));
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
        return todos;
    }
    
    public void transferir(String email, String email2, float valor){
        Connection con=null;
        try{
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();
            st.executeUpdate("UPDATE `situacao_irregular` SET `Valor`=`Valor`+"+valor +
                " WHERE `user_email` = '"+ email +"' AND email='"+email2 + "';");
            st.executeUpdate("UPDATE `situacao_irregular` SET `Valor`=`Valor`-"+valor +
                " WHERE `user_email` = '"+ email2+"' AND email='"+email + "';");
            DAOUtilizador daoU= new DAOUtilizador();
            daoU.atualizarSaldo(email);
            daoU.atualizarSaldo(email2);
        }catch(Exception e){ e.printStackTrace();}        
        finally {
            try {
                con.close();
            } catch (Exception e) { e.printStackTrace();}
        }
    }
    
}

