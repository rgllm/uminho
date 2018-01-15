package com.rgllm.trader;

import com.mongodb.BasicDBList;
import com.mongodb.MongoClientURI;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import java.util.List;
import java.util.ArrayList;
import java.util.TreeMap;

public class userDAO {
    
    public static void inserirUser(User novo){
        
        MongoClientURI uri = new MongoClientURI("mongodb://java:fLN?]Nu~;8-@ds231315.mlab.com:31315/traderapp");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB( "traderapp" );
        DBCollection coll = db.getCollection("users");
        
       
       ArrayList<CFD> portfolio = new ArrayList<>(novo.getPortfolio());
       List<Object> portfolioM = new BasicDBList();
        
       portfolio.stream().map((cfd) -> {
           DBObject sCfd = new BasicDBObject();
           sCfd.put("time",cfd.getTime());
           sCfd.put("company",cfd.getCompany());
           sCfd.put("rate",cfd.getRate());
           sCfd.put("type",cfd.getType().name());
           sCfd.put("units",cfd.getUnits());
           sCfd.put("stop_loss",cfd.getStop_loss());
           sCfd.put("take_profit",cfd.getTake_profit());
            return sCfd;
        }).forEachOrdered((sCfd) -> {
            portfolioM.add(sCfd);
        });
        
        DBObject sUser = new BasicDBObject();
        sUser.put("email", novo.getEmail());
        sUser.put("nome",novo.getNome());
        sUser.put("password", novo.getPassword());
        sUser.put("saldo", novo.getSaldo());
        sUser.put("watchlist", novo.getWatchList());
        sUser.put("portfolio",portfolioM);
        
                
        coll.insert(sUser);
       
    }
    
    public static boolean existeUser(User user){
        
        MongoClientURI uri = new MongoClientURI("mongodb://java:fLN?]Nu~;8-@ds231315.mlab.com:31315/traderapp");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB( "traderapp" );
        DBCollection coll = db.getCollection("users");
        
        DBObject sUser = new BasicDBObject();
        sUser.put("email", user.getEmail());
        DBCursor cur = coll.find(sUser);
        
        return cur.size()>0;
    }
    
    public static boolean loginUser(String email, String password){
        
        MongoClientURI uri = new MongoClientURI("mongodb://java:fLN?]Nu~;8-@ds231315.mlab.com:31315/traderapp");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB( "traderapp" );
        DBCollection coll = db.getCollection("users");
        
        DBObject sUser = new BasicDBObject();
        sUser.put("email", email);
        sUser.put("password",password);
        DBCursor cur = coll.find(sUser);
        
        return cur.size()>0;
    }
    
    public static TreeMap<String,User> getAllUsers(){
       TreeMap<String,User> rt = new TreeMap<>();
        
        MongoClientURI uri = new MongoClientURI("mongodb://java:fLN?]Nu~;8-@ds231315.mlab.com:31315/traderapp");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB( "traderapp" );
        DBCollection coll = db.getCollection("users");
        
        DBCursor cur = coll.find();
        if( cur.hasNext() ){
            DBObject dbObject = cur.next();
            String email = (String) dbObject.get("email");
            String nome = (String) dbObject.get("nome");
            String password = (String) dbObject.get("password");
            double saldo = (double) dbObject.get("saldo");
            BasicDBList nWatchlist = (BasicDBList) dbObject.get("watchlist");
            ArrayList<String> watchlist = new ArrayList<>();
            nWatchlist.forEach((el) -> {
                watchlist.add((String) el);
            });

            BasicDBList nPortfolio = (BasicDBList) dbObject.get("portfolio");
            ArrayList<CFD> portfolio = new ArrayList<>();
            for(int i =0;i<nPortfolio.size();i++){
                BasicDBObject cfdObj = (BasicDBObject) nPortfolio.get(i);
                CFD novo=null;
                String company = cfdObj.getString("company");
                double rate = cfdObj.getDouble("rate");
                String type = cfdObj.getString("type");
                int units = cfdObj.getInt("units");
                double stop_loss = cfdObj.getDouble("stop_loss");
                double take_profit = cfdObj.getDouble("take_profit");
                if(type.equals("Buy")){
                    novo = new CFD(company,rate,CFDtype.Buy,units,stop_loss,take_profit);
                }
                else{
                    novo = new CFD(company,rate,CFDtype.Sell,units,stop_loss,take_profit);
                }
                if(novo!=null) portfolio.add(novo);
            }
            User toAdd = new User(email,nome,password,saldo,watchlist,portfolio);
            rt.put(email,toAdd);
        }
        return rt;
    }
    
    public static User devolveUser(String email){
        
        MongoClientURI uri = new MongoClientURI("mongodb://java:fLN?]Nu~;8-@ds231315.mlab.com:31315/traderapp");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB( "traderapp" );
        DBCollection coll = db.getCollection("users");
        
        DBObject sUser = new BasicDBObject();
        sUser.put("email", email);
        DBCursor cur = coll.find(sUser);
        DBObject dbObject = cur.next();
        String nEmail = (String) dbObject.get("email");
        String nome = (String) dbObject.get("nome");
        String password = (String) dbObject.get("password");
        double saldo = Double.parseDouble(dbObject.get("saldo").toString());
        BasicDBList nWatchlist = (BasicDBList) dbObject.get("watchlist");
        ArrayList<String> watchlist = new ArrayList<>();
        nWatchlist.forEach((el) -> {
            watchlist.add((String) el);
        });
        
        BasicDBList nPortfolio = (BasicDBList) dbObject.get("portfolio");
        ArrayList<CFD> portfolio = new ArrayList<>();
        for(int i =0;i<nPortfolio.size();i++){
            BasicDBObject cfdObj = (BasicDBObject) nPortfolio.get(i);
            CFD novo=null;
            String company = cfdObj.getString("company");
            double rate = cfdObj.getDouble("rate");
            String type = cfdObj.getString("type");
            int units = cfdObj.getInt("units");
            double stop_loss = cfdObj.getDouble("stop_loss");
            double take_profit = cfdObj.getDouble("take_profit");
            if(type.equals("Buy")){
                novo = new CFD(company,rate,CFDtype.Buy,units,stop_loss,take_profit);
            }
            else{
                novo = new CFD(company,rate,CFDtype.Sell,units,stop_loss,take_profit);
            }
            if(novo!=null) portfolio.add(novo);
        }
        
        return(new User(nEmail,nome,password,saldo,watchlist,portfolio));
    
    }
    
    public static void addToWatchlist(String email, String company ){
        
        MongoClientURI uri = new MongoClientURI("mongodb://java:fLN?]Nu~;8-@ds231315.mlab.com:31315/traderapp");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB( "traderapp" );
        DBCollection coll = db.getCollection("users");
        BasicDBObject query = new BasicDBObject();
        query.put("email",email);
        BasicDBObject push = new BasicDBObject();
        push.put("$addToSet",
            new BasicDBObject("watchlist",company));
        coll.update(query, push);
    }
    
    public static void addCFD(String email, CFD toAdd){
        MongoClientURI uri = new MongoClientURI("mongodb://java:fLN?]Nu~;8-@ds231315.mlab.com:31315/traderapp");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB( "traderapp" );
        DBCollection coll = db.getCollection("users");
        
        BasicDBObject query = new BasicDBObject();
        query.put("email",email);
        BasicDBObject push = new BasicDBObject();
        push.put("$push",
            new BasicDBObject("portfolio",
                new BasicDBObject("company",toAdd.getCompany())
                                  .append("rate",toAdd.getRate())
                                  .append("type",toAdd.getType().toString())
                                  .append("units",toAdd.getUnits())
                                  .append("stop_loss", toAdd.getStop_loss())
                                  .append("take_profit",toAdd.getTake_profit())));
        
        coll.update(query, push);
    }
    
    public static void removeCFD(String email, CFD toAdd){
        
        MongoClientURI uri = new MongoClientURI("mongodb://java:fLN?]Nu~;8-@ds231315.mlab.com:31315/traderapp");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB( "traderapp" );
        DBCollection coll = db.getCollection("users");
        
        BasicDBObject query = new BasicDBObject();
        query.put("email",email);
        BasicDBObject push = new BasicDBObject();
        push.put("$pull",
            new BasicDBObject("portfolio",
                new BasicDBObject("company",toAdd.getCompany())
                                  .append("rate",toAdd.getRate())
                                  .append("type",toAdd.getType().toString())
                                  .append("units",toAdd.getUnits())
                                  .append("stop_loss", toAdd.getStop_loss())
                                  .append("take_profit",toAdd.getTake_profit())));
        
        coll.update(query, push);
    }
    
    public static void atualizarSaldo(String email, double nSaldo){
        MongoClientURI uri = new MongoClientURI("mongodb://java:fLN?]Nu~;8-@ds231315.mlab.com:31315/traderapp");
        MongoClient client = new MongoClient(uri);
        DB db = client.getDB( "traderapp" );
        DBCollection coll = db.getCollection("users");
        
        BasicDBObject query = new BasicDBObject();
        query.put("email",email);
        BasicDBObject push = new BasicDBObject();
        push.put("$set",
            new BasicDBObject("saldo",nSaldo));
        
        coll.update(query, push);
    }
    
}