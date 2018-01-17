/*
 * To change this license header, choose License Headetablespaces in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oracleBD;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author rgllm
 */
public class Main {
     
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
        
                /* List of tablespaces */
            @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
            private static List<Tablespaces> list_tablespaces = new ArrayList(); 
                /* List of datafiles */
            @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
            private static List<Datafiles> list_datafiles = new ArrayList();
             /* List of users */
            @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
            private static List<Users> list_users = new ArrayList();
             /* List of tables*/
            @SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
            private static List<Tables> list_tables = new ArrayList();
            
            @SuppressWarnings("FieldMayBeFinal")
            private static Memory m = new Memory();
       
            @SuppressWarnings({"FieldMayBeFinal", "MismatchedQueryAndUpdateOfCollection"})
            private static List<Sessions> list_sessions = new ArrayList();
            
            
            @SuppressWarnings({"FieldMayBeFinal", "MismatchedQueryAndUpdateOfCollection"})
            private static List<IOReads> io_reads = new ArrayList();
       
            @SuppressWarnings({"FieldMayBeFinal", "MismatchedQueryAndUpdateOfCollection"})
            private static List<IOWrites> io_writes = new ArrayList();
            
           private static String query;
           private static String updateQuery;
           private static int result;
            
            @SuppressWarnings("null")
    
            public static void orcl_connection() throws SQLException{     
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
                System.out.println("Where is your Oracle JDBC Driver?");
                e.printStackTrace();
                return;
        }
         @SuppressWarnings("UnusedAssignment")
        Connection connection = null;
        Connection connection2 = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "system", "oracle");
        } catch (SQLException e) {
            System.out.println("Connection 1 Failed! Check output console");
            e.printStackTrace();
            return;
        }
        try {
            //Conexão à nossa BD
            connection2 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "mic", "mic");
        } catch (SQLException e) {
            System.out.println("Connection 2 Failed! Check output console");
            e.printStackTrace();
            return;
        }
        
        if (connection != null) {
            System.out.println("Connected to pluggable database (get data)");
        } else {
            System.out.println("Failed to make connection with connection 1!");
        }
        
        if (connection2 != null) {
            System.out.println("Connected to assignment database (insert data)");
        } else {
            System.out.println("Failed to make connection with connection 2!");
        }
        
            @SuppressWarnings("null")
        Statement stnt = connection.createStatement();
        ResultSet tablespaces = stnt.executeQuery("select fs.tablespace_name,(df.totalspace - fs.freespace),fs.freespace ,df.totalspace, round(100 * (fs.freespace / df.totalspace)) from" +
         " (select tablespace_name,round(sum(bytes) / 1048576) TotalSpace from dba_data_files group by tablespace_name) df," +
         " (select tablespace_name,round(sum(bytes) / 1048576) FreeSpace from dba_free_space group by tablespace_name) fs" +
         " where df.tablespace_name = fs.tablespace_name");
     
        while(tablespaces.next()){
            @SuppressWarnings("UnusedAssignment")
            Tablespaces t = new Tablespaces();
            t = new Tablespaces(tablespaces.getString(1),tablespaces.getInt(3),tablespaces.getInt(2),tablespaces.getFloat(5),tablespaces.getInt(4));
            list_tablespaces.add(t);
        }
        
        Statement stmt = connection2.createStatement();
        
        for(Tablespaces t: list_tablespaces){      
            updateQuery = "UPDATE tablespaces "
                        + "SET used_mb = " + t.getUsed_space() + ","
                        + " free_mb = " + t.getFree_space() + ","
                        + " total_mb = " + t.getTotal_used() + ","
                        + " percentage_used = " + t.getPercentage_used() + ","
                        + " timestamp = CURRENT_TIMESTAMP"
                        + " WHERE tablespace_name = " + "'" + t.getName() + "'";
            
           
            result = stmt.executeUpdate(updateQuery);
            if(result == 0){
                    query = "INSERT INTO tablespaces(tablespace_name, used_mb, free_mb, total_mb, percentage_used, timestamp)" +
                        " VALUES("
                        + "'" + t.getName() + "'" + "," 
                        + t.getUsed_space() + ","
                        + t.getFree_space() + ","
                        + t.getTotal_used() + ","
                        + t.getPercentage_used() + ","
                        + "CURRENT_TIMESTAMP)";
                    
                    stmt.execute(query);
            }
           
            }
                    
        
        
        ResultSet datafiles = stnt.executeQuery("SELECT df.file_id \"File ID\",\n" +
"       Substr(df.tablespace_name,1,20) \"Tablespace Name\",\n" +
"        Substr(df.file_name,1,80) \"File Name\",\n" +
"        Round(df.bytes/1024/1024,0) \"Size (M)\",\n" +
"        decode(e.used_bytes,NULL,0,Round(e.used_bytes/1024/1024,0)) \"Used (M)\",\n" +
"        decode(f.free_bytes,NULL,0,Round(f.free_bytes/1024/1024,0)) \"Free (M)\",\n" +
"        decode(e.used_bytes,NULL,0,Round((e.used_bytes/df.bytes)*100,0)) \"% Used\"\n" +
"FROM    DBA_DATA_FILES DF,\n" +
"       (SELECT file_id,\n" +
"               sum(bytes) used_bytes\n" +
"        FROM dba_extents\n" +
"        GROUP by file_id) E,\n" +
"       (SELECT Max(bytes) free_bytes,\n" +
"               file_id\n" +
"        FROM dba_free_space\n" +
"        GROUP BY file_id) f\n" +
"WHERE    e.file_id (+) = df.file_id\n" +
"AND      df.file_id  = f.file_id (+)\n" +
"ORDER BY df.tablespace_name,\n" +
"         df.file_name");
        
        while(datafiles.next()){
            /* Find tablespace for datafile */
            Tablespaces tb = null;
            for (int i = 0 ; i < list_tablespaces.size() ; i++){
                tb = list_tablespaces.get(i);
                if(tb.getName().equals(datafiles.getString(2)))
                    break;
            }
            Datafiles d = new Datafiles(datafiles.getInt(1),tb,datafiles.getString(3),datafiles.getInt(6),
            datafiles.getInt(4),datafiles.getInt(5),datafiles.getFloat(7));
            list_datafiles.add(d);
        } 
        
        
        for(Datafiles d: list_datafiles){
        
            updateQuery = "UPDATE datafiles SET "
                            + " total_mb = " + d.getSize() + ","
                            + " used_mb = " + d.getUsed_space() + ","  
                            + " free_mb = " + d.getFree_space() + ","
                            + " percentage_used = " + d.getPercentage_used() + "," 
                            + " timestamp = CURRENT_TIMESTAMP"
                            + " WHERE file_id = "  + d.getFile_id();
        
            result = stmt.executeUpdate(updateQuery);
            if(result == 0){
                query = "INSERT INTO datafiles(file_id, datafile_name, tablespace_name, total_mb, used_mb, free_mb, percentage_used, timestamp)"
                        + "VALUES("
                        + d.getFile_id() + ","
                        + "'" + d.getName() + "'" + ","
                        + "'" + d.getTablespace().getName() +  "'" + ","
                        + d.getSize() + ","
                        + d.getUsed_space() + ","  
                        + d.getFree_space() + ","
                        + d.getPercentage_used() + "," 
                        + "CURRENT_TIMESTAMP)";
                stmt.execute(query);
            }
        }
        
        ResultSet users = stnt.executeQuery("select user_id, username, default_tablespace,"
                + "temporary_tablespace, account_status from dba_users");
        while(users.next()){
            @SuppressWarnings("UnusedAssignment")
            Users u = new Users();
            u = new Users(users.getInt(1),users.getString(2),users.getString(3),users.getString(4),
            users.getString(5));
            list_users.add(u);
        }
        ResultSet user_quota = stnt.executeQuery("select username, tablespace_name, bytes from dba_ts_quotas");
         while(user_quota.next()){
               /* Find user from user data */
            int result = 0;
            Users u = null;
            for (int i = 0 ; i < list_users.size() ; i++){
                u = list_users.get(i);
                if(u.getUser().equals(user_quota.getString(1))){
                    result = 1;
                    break;
                }
            }
            if(result == 1){ u.setTabName(user_quota.getString(2));
                             u.setQb(user_quota.getInt(3));
            }  
         }
        ResultSet user_priv = stnt.executeQuery("select grantee, privilege from dba_sys_privs");
         while(user_priv.next()){
               /* Find active user from user data */
            int result = 0;
            Users u = null;
            for (int i = 0 ; i < list_users.size() ; i++){
                u = list_users.get(i);
                if(u.getUser().equals(user_priv.getString(1))){
                    result = 1;
                    break;
                }
            }
            if(result == 1){ u.setPriv(user_priv.getString(2));}  
         }
        ResultSet active_user = stnt.executeQuery("SELECT\n" +
"   s.username,\n" +
"   t.sid,\n" +
"   s.serial#,\n" +
"   SUM(VALUE/100) as \"cpu usage (seconds)\"\n" +
"FROM\n" +
"   v$session s,\n" +
"   v$sesstat t,\n" +
"   v$statname n\n" +
"WHERE\n" +
"   t.STATISTIC# = n.STATISTIC#\n" +
"AND\n" +
"   NAME like '%CPU used by this session%'\n" +
"AND\n" +
"   t.SID = s.SID\n" +
"AND\n" +
"   s.username is not null\n" +
"GROUP BY username,t.sid,s.serial#");
        while(active_user.next()){
             /* Find active user from user data */
            int result = 0;
            Users u = null;
            for (int i = 0 ; i < list_users.size() ; i++){
                u = list_users.get(i);
                if(u.getUser().equals(active_user.getString(1))){
                    result = 1;
                    break;
                }
            }
            if(result == 1){
                u.setSid(active_user.getString(2));
                u.setSerial(active_user.getString(3));
                u.setCPU(active_user.getString(4));
            }    
        }
        
        for(Users u:list_users){
        
            updateQuery = "UPDATE users SET"
                            + " account_status = " + "'" + u.getAccStat() + "'" + ","
                            + " cpu_usage = " + u.getCPU() + ","
                            + " timestamp = CURRENT_TIMESTAMP " 
                            + " WHERE user_id = " + u.getUser_id();
            
            result = stmt.executeUpdate(updateQuery);
            if (result == 0){
                query = "INSERT INTO users(user_id, name, default_tablespace, temporary_tablespace,"
                    + "account_status, quota, privilege, cpu_usage, timestamp)"
                    + "VALUES("
                    + u.getUser_id() + ","
                    + "'" + u.getUser() + "'" + ","
                    + "'" + u.getDefTab() + "'" + ","
                    + "'" + u.getTempTab() + "'" + ","
                    + "'" + u.getAccStat() + "'" + ","
                    + u.getQb() + ","
                    + "'" + u.getPriv() + "'" + ","
                    + u.getCPU() + ","
                    + "CURRENT_TIMESTAMP)";
                stmt.execute(query);
            }
        }
        
         ResultSet tables =  stnt.executeQuery("select owner, table_name,dropped,tablespace_name,num_rows from dba_tables ORDER BY num_rows");
          while(tables.next()){
              @SuppressWarnings("UnusedAssignment")
              Tables tb = new Tables();
              tb = new Tables(tables.getString(1),tables.getString(4),tables.getString(2),tables.getString(3),tables.getInt(5));
              list_tables.add(tb);
          }
           
         ResultSet tables_access =  stnt.executeQuery("SELECT t.owner, t.table_name, lr.VALUE + pr.VALUE AS total_reads\n" +
"FROM (SELECT	owner, object_name, VALUE\n" +
"FROM	v$segment_statistics\n" +
"WHERE	statistic_name = 'logical reads') lr, (SELECT owner, object_name, VALUE\n" +
"FROM	v$segment_statistics\n" +
"WHERE	statistic_name = 'logical reads') pr, dba_tables t\n" +
"WHERE lr.owner = pr.owner AND lr.object_name = pr.object_name AND lr.owner = t.owner AND lr.object_name = t.table_name\n" +
"ORDER BY 3 desc");
         while(tables_access.next()){
              for (int i = 0 ; i < list_tables.size() ; i++){
                @SuppressWarnings("UnusedAssignment")
                Tables t = null;
                t = list_tables.get(i);
                if(t.getName().equals(tables_access.getString(2))){
                    t.setNA(tables_access.getInt(3));
                    break;
                }
            }
         }
         
         ResultSet usrs = stnt.executeQuery("SELECT username, user_id\n" + 
                 "FROM dba_users");
         
         while(usrs.next()){
             for (int i = 0 ; i < list_tables.size() ; i++){
                @SuppressWarnings("UnusedAssignment")
                Tables t = null;
                t = list_tables.get(i);
                if(t.getOwner().equals(usrs.getString(1))){
                    t.setOwner_id(usrs.getInt(2));
                    break;
                }
            }
             
         }
         
         
             for(Tables t:list_tables){
                 
             updateQuery = "UPDATE tables SET"
                     + " nr_of_accesses = " + t.getNA() + ","
                     + " nr_of_regists = " + t.getNR() + ","
                     + " timestamp = CURRENT_TIMESTAMP"
                     + " WHERE owner_id = " + t.getOwner_id() 
                     + " AND name = " + "'" +  t.getName() + "'";
             
             result = stmt.executeUpdate(updateQuery);
             if (result == 0){
                 query = "INSERT INTO tables(owner_name, owner_id, name, correspondent_tablespace," 
                     + "nr_of_accesses, nr_of_regists, dropped, timestamp)"
                     + " VALUES("
                     + "'" + t.getOwner() + "'" + ","
                     + "'" + t.getOwner_id() + "'" + ","
                     + "'" + t.getName() + "'" + ","
                     + "'" + t.getTablespace() + "'" + ","
                     + t.getNA() + ","
                     + t.getNR() + ","
                     + "'" + t.getER() + "'" + ","
                     + "CURRENT_TIMESTAMP)";
             stmt.execute(query);
            }
             
                 
            }
             
       
        ResultSet session =  stnt.executeQuery("SELECT sid, UserName, SchemaName, Logon_Time\n" +
"FROM V$Session\n" +
"WHERE\n" +
"Status='ACTIVE' AND\n" +
"UserName IS NOT NULL");
        while(session.next()){
            for (int i = 0 ; i < list_users.size() ; i++){
                @SuppressWarnings("UnusedAssignment")
                Users u = null;
                u = list_users.get(i);
                if(u.getUser().equals(session.getString(2))){
                    Sessions s = new Sessions(session.getInt(1), u,session.getString(3),session.getString(4));
                    list_sessions.add(s);
                    break;
                }
            }
        }
        
        for(Sessions s:list_sessions){
            
            updateQuery = "UPDATE sessions SET"
                            + " login_time = " + "'" + s.getLt() + "'" + ","
                            + " timestamp = CURRENT_TIMESTAMP"
                            + " WHERE session_id = " + s.getSID();
            
            
            result = stmt.executeUpdate(updateQuery);
            if (result == 0){
                query = "INSERT INTO sessions(session_id, username, user_id, schema_name, login_time, timestamp)"
                        + " VALUES("
                        + s.getSID() + ","
                        + "'" + s.getUsers().getUser() + "'" + ","
                        + s.getUsers().getUser_id() + ","
                        + "'" + s.getSn() + "'" + ","
                        + "'" + s.getLt() + "'" + ","
                        + "CURRENT_TIMESTAMP)";
                stmt.execute(query);
            }
        }
       
        
        
        
        connection.close();  
    }
     public static void orcl12c_connection() throws SQLException {
       
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
                System.out.println("Where is your Oracle JDBC Driver?");
                e.printStackTrace();
                return;
        }
         @SuppressWarnings("UnusedAssignment")
        Connection connection = null;
        Connection assignment_con = null;
        
        try {
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl12c", "system", "oracle");
        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();
            return;
        }
         try {
            //Conexão à nossa BD
            assignment_con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/orcl", "mic", "oracle");
        } catch (SQLException e) {
            System.out.println("Connection 2 Failed! Check output console");
            e.printStackTrace();
            return;
        }
        
        if (connection != null) {
            System.out.println("Connected to root database (get data)");
        } else {
            System.out.println("Failed to make connection with connection 1!");
        }
        
        if (assignment_con != null) {
            System.out.println("Connected to assignment database (insert root data)");
        } else {
            System.out.println("Failed to make connection!");
        }
        @SuppressWarnings("null")
        Statement stnt = connection.createStatement();
        Statement stmt = assignment_con.createStatement();
        
        ResultSet sga = stnt.executeQuery("select (sga+pga)/1024/1024 as \"sga_pga\"\n" +
"from \n" +
"(select sum(value) sga from v$sga),\n" +
"(select sum(pga_used_mem) pga from v$process)");
        
          double total = 0;
         while(sga.next()){
            total = sga.getDouble(1);
         }
          ResultSet sga2 = stnt.executeQuery("select sum(bytes)/1024 from v$sgastat where name = 'free memory'");
         while(sga2.next()){
            double bytes = sga2.getDouble(1)/1024;
            m = new Memory(total,bytes,bytes/total); 
         }
       
         
        query = "INSERT INTO memory(total_size_bytes,free_size_bytes, percentage_free, timestamp)"
                     + " VALUES("
                     + m.total_bytes + ","
                     + m.free_bytes + ","
                     + m.percentage_free_bytes + ","
                     + "CURRENT_TIMESTAMP)";
        stmt.execute(query);
        
         ResultSet ioreads = stnt.executeQuery("select metric_name,begin_time,end_time,value from v$sysmetric_history "
                  + "where metric_name = 'Physical Reads Per Sec' order by begin_time");  
         while(ioreads.next()){
             IOReads r = new IOReads(ioreads.getString(1),ioreads.getString(2), ioreads.getString(3),ioreads.getDouble(4));
             io_reads.add(r);
         }
         
         for(IOReads ir:io_reads){
             query = "INSERT INTO io_reads(metric_name,begin_time,end_time,value,timestamp)"
                        + " VALUES("
                        + "'" + ir.getName() + "'" + ","
                        + "to_timestamp('"+ir.getBt() + "', 'yyyy-mm-dd hh24:mi:ss.FF')" + ","
                        + "to_timestamp('"+ir.getEt() + "', 'yyyy-mm-dd hh24:mi:ss.FF')" + ","
                        + ir.getValue() + ","
                        + "CURRENT_TIMESTAMP)";
             stmt.execute(query);
         }
         
         
         ResultSet iowrites = stnt.executeQuery("select metric_name,begin_time,end_time,value from v$sysmetric_history "
                 + "where metric_name = 'Physical Writes Per Sec' order by begin_time");
         while(iowrites.next()){
             IOWrites w = new IOWrites(iowrites.getString(1),iowrites.getString(2), iowrites.getString(3),iowrites.getDouble(4));
             io_writes.add(w);
         }
         
         for(IOWrites iw:io_writes){
             query = "INSERT INTO io_writes(metric_name,begin_time,end_time,value,timestamp)"
                        + "VALUES("
                        + "'" + iw.getName() + "'" + ","
                        + "to_timestamp('"+iw.getBt() + "', 'yyyy-mm-dd hh24:mi:ss.FF')" + ","
                        + "to_timestamp('"+iw.getEt() + "', 'yyyy-mm-dd hh24:mi:ss.FF')" + ","
                        + iw.getValue() + ","
                        + "CURRENT_TIMESTAMP) ";
         }
         stmt.execute(query);
         
        
         
         
     }
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    @SuppressWarnings("null")
    public static void main(String[] args) throws SQLException {
        Timer t = new Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                try {   
                    orcl_connection();
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    orcl12c_connection();
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            }, 0, 30000);          
        }
    }