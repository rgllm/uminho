package com.rgllm.trader;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import java.util.ArrayList;

public class notificationsDAO {
    
    private static DBCollection coll = new MongoConnection("notifications").getColl();
    
    public static void addNotification(Asset a){
        
        DBObject newAsset = new BasicDBObject();
        newAsset.put("email",a.getEmail());
        newAsset.put("company",a.getCompany());
        newAsset.put("price",a.getEspec_price());
        newAsset.put("state",a.getState());
        
        coll.insert(newAsset);
    }
    
    public static ArrayList<Asset> getNotifications(){     
        
        ArrayList<Asset> rt = new ArrayList<>();
        
        DBCursor cur = coll.find();
        while(cur.hasNext()){
            DBObject nots = cur.next();
            String email = (String) nots.get("email");
            String company = (String) nots.get("company");
            double espec_price = Double.parseDouble(nots.get("price").toString());
            boolean state = (boolean) nots.get("state");
            Asset toAdd = new Asset(email,company,espec_price,state);
            rt.add(toAdd);
        }
        return rt;
    }
    
    public static void removeNotification(Asset a){
        
        DBObject toRemove = new BasicDBObject();
        toRemove.put("email",a.getEmail());
        toRemove.put("company",a.getCompany());
        toRemove.put("price",a.getEspec_price());
        toRemove.put("state",a.getState());
        
        coll.remove(toRemove);
        
    }
    
    public static void changeNotificationState(Asset a){

        BasicDBObject query = new BasicDBObject();
        query.put("email",a.getEmail());
        query.put("company",a.getCompany());
        query.put("price",a.getEspec_price());
        BasicDBObject push = new BasicDBObject();
        push.put("$set", 
            new BasicDBObject("state",true));
        coll.update(query, push);
    }
    
    
}
