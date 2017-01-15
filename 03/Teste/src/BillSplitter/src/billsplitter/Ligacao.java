/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billsplitter;
import java.sql.*;

public class Ligacao {
    private final String URL = "jdbc:mysql://127.0.0.1/billsplitter";
    private final String USER = "billsplitter";
    private final String PASS = "pass";
    private Connection con;


    public Ligacao() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con =  DriverManager.getConnection(URL, USER, PASS);
            System.out.println("sucesso");
        }catch (Exception e) { // SQLException ou ClassNotFoundException
            System.out.println("ERROR!");
            e.printStackTrace();
        }
    }
    
    public Connection getConnection(){
        return con;
    }
    
}

            /* ADICIONAR CASA
            Statement quim= con.createStatement();
            int rs= quim.executeUpdate("INSERT INTO `billsplitter`.`casa` (`idcasa`, `morada`, `cod_postal`, `localidade`, `telefone`, `observacoes`)"
                                                                    + "VALUES (1, 'asdfsadf', 'asdf', 'asdf', 'asdf', 'asdf');");
*/
            /* CONSULTAR CASAS
            Statement quim= con.createStatement();
            ResultSet rs= quim.executeQuery("select * from casa;");
            while(rs.next()){
                System.out.println(rs.getString("morada"));
            }
*/