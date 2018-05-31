package Data;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CurrenciesDB {

    private String host;
    private Integer port;
    private String db_name;

    private String user;
    private String password;

    public CurrenciesDB(String host, Integer port, String db_name, String user, String password) {
        this.host = host;
        this.port = port;
        this.db_name = db_name;
        this.user = user;
        this.password = password;
    }

    MongoClient mongo;
    MongoDatabase database;

    public void connect() {
        // Creating a Mongo client
        mongo = new MongoClient( host , port );

        // Creating Credentials
        MongoCredential credential;
        credential = MongoCredential.createCredential(user, db_name, password.toCharArray());
        System.out.println("[MongoDB] Connected to the database successfully.");

        // Accessing the database
        database = mongo.getDatabase(db_name);
        System.out.println("Credentials :: "+ credential);
    }

    private boolean isEmpty(){
        return database.getCollection("currencies").count() > 0;
    }

    public boolean init(ArrayList<Currency> list){
        List<org.bson.Document> docList = list.stream().map(X -> X.toDoc()).collect(Collectors.toList());

        if (!isEmpty())
            database.getCollection("currencies").deleteMany(new BasicDBObject());

        database.getCollection("currencies")
               .insertMany(docList);
        return true;
    }


}
