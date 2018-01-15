package com.rgllm.trader;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoConnection {
    private final MongoClientURI uri = new MongoClientURI("mongodb://java:fLN?]Nu~;8-@ds231315.mlab.com:31315/traderapp");
    private final MongoClient client = new MongoClient(uri);
    private final DB db = client.getDB( "traderapp" );
    private final DBCollection coll;

    public MongoConnection(String name) {
        coll = db.getCollection(name);
    }

    public DBCollection getColl() {
        return coll;
    }
    
}
