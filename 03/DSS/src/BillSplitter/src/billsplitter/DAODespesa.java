package billsplitter;

import java.sql.*;
import java.util.*;
import java.util.Map.Entry;

public class DAODespesa{

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

    public int lastId(){
        Connection con=null;
        try{
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT `iddespesa` FROM `despesa_normal` ORDER BY iddespesa DESC LIMIT 1");
            if(rs.next())
                return rs.getInt("iddespesa");
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

    public void insert(Despesa d){
        Connection con=null;
        String descricao=d.getDescricao();
        java.util.Date data_inicio=d.getData_inicio();
        java.util.Date data_fim=d.getData_fim();
        java.util.Date data_limite=d.getData_limite();
        float montante=d.getMontante();
        String observacoes=d.getObservacoes();
        String pagador=d.getPagador();//email do utilizador que pagou
        int idcasa=d.getIdcasa();
        HashMap<String,Float> participantes=new HashMap<>(d.getParticipantes());
        try{
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();
            int ret = st.executeUpdate("INSERT INTO `despesa_normal` (`descricao`,`data_inicio`,`data_fim`,`data_limite`,`montante`,`observacoes`,`estado`,`casa_idcasa`)"
                +"VALUES('"+descricao+"', '"+convertDateToSQL(data_inicio)+"', '"+convertDateToSQL(data_fim)+"', '"+convertDateToSQL(data_limite)+"', '"+montante+"', '"+observacoes+"', '"+0+"', '"+idcasa + "');");
            int iddespesa=lastId();
            for(Entry<String,Float> e: participantes.entrySet()){
                ret=st.executeUpdate("INSERT INTO `despesa_has_user` (`despesa_user_id`,`user_email`,`valor`)"
                                     +"VALUES('"+iddespesa+"', '"+ e.getKey()+"', '"+ e.getValue() +"');");
            }
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

    public void pagarDespesa(int idDespesa, String pagadorID){
        HashMap<String,Float> situacoes=new HashMap<>();
        Connection con=null;
        ArrayList<String> emails=new ArrayList<>();
        try{
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();
            ResultSet rs = st.executeQuery("SELECT user_email,valor FROM `despesa_has_user` WHERE despesa_user_id="+idDespesa+";");
            ArrayList<Float> aux=new ArrayList<>();
            while(rs.next()){
                emails.add(rs.getString("user_email"));
                aux.add(rs.getFloat("valor"));
            }
            for(int i = 0; i<aux.size();i++)
                if(!emails.get(i).equals(pagadorID)){
                    situacoes.put(emails.get(i),aux.get(i));
                    st.executeUpdate("UPDATE `situacao_irregular` SET `Valor`=`Valor`-"+aux.get(i) +
                        " WHERE `user_email` = '"+ emails.get(i) +"' AND email='"+pagadorID + "';");
                    st.executeUpdate("UPDATE `situacao_irregular` SET `Valor`=`Valor`+"+aux.get(i) +
                        " WHERE `user_email` = '"+ pagadorID+"' AND email='"+emails.get(i) + "';");
                }
            DAOUtilizador daoU= new DAOUtilizador();
            emails.add(pagadorID);
            st.executeUpdate("UPDATE `despesa_normal` SET `estado`=1"
                        +" WHERE `iddespesa` = "+ idDespesa+";");
            for(String s : emails)
                daoU.atualizarSaldo(s);
        }catch(Exception e){ e.printStackTrace();}        
        finally {
            try {
                con.close();
            } catch (Exception e) { e.printStackTrace();}
        }
    }

    public Despesa get(Object key){
        Connection con=null;
        Despesa nova=null;
        int id;
        String descricao,observacoes,pagador;
        java.util.Date data_inicio,data_fim,data_limite;
        float montante;
        Boolean estado;
        int idcasa;
        HashMap<String,Float> participantes= new HashMap<>();
        try{
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();
            int chave=Integer.parseInt(key.toString());
            ResultSet rs=st.executeQuery("SELECT * FROM `despesa_normal` WHERE `iddespesa`="+chave+";");
            // assume-se que a query retorna smpre pelo menos uma linha
            rs.next();
            id= rs.getInt("iddespesa");
            descricao=rs.getString("descricao");
            data_inicio=convertDateToJava(rs.getDate("data_inicio"));
            data_fim=convertDateToJava(rs.getDate("data_fim"));
            data_limite=convertDateToJava(rs.getDate("data_limite"));
            montante=rs.getFloat("montante");
            observacoes=rs.getString("observacoes");
            pagador=rs.getString("pagador");
            if(rs.getInt("estado")==0){estado=false;} else{estado=true;};
            idcasa=rs.getInt("casa_idcasa");
            
            rs=st.executeQuery("SELECT user_email,valor FROM `despesa_has_user` WHERE despesa_user_id="+id+";");
            if(rs.next()){
                participantes.put(rs.getString("user_email"),rs.getFloat("valor"));
            }
            nova=new Despesa(id, descricao, data_inicio, data_fim, data_limite , montante, observacoes, pagador, estado, idcasa, participantes);
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

    public ArrayList<Despesa> getDespesasCasa(int idcasa){
        ArrayList<Despesa> todas=new ArrayList<>();
        ArrayList<Integer> ids=new ArrayList<>();
        Connection con=null;
        try{
            con=new Ligacao().getConnection();
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT `iddespesa` FROM `despesa_normal` WHERE `casa_idcasa`="+idcasa+";");
            while(rs.next()){
                ids.add(rs.getInt("iddespesa"));
            }
            for(int x: ids){
                todas.add(get(x));
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
        return todas;
    }
}
