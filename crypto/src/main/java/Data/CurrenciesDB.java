package Data;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.Arrays;
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


    public static void insertDB(ArrayList<Currency> list){
        MongoCredential credential = MongoCredential.createCredential("trding", "trding_users", "trding2018".toCharArray());
        MongoClient mongo = new MongoClient(
                new ServerAddress("ds141720.mlab.com", 41720),
                Arrays.asList(credential)
        );

        System.out.println("Credentials :: "+ credential);
        System.out.println("[MongoDB] Connected to the database successfully.");
        MongoDatabase database = mongo.getDatabase("trding_users");

        List<org.bson.Document> docList = list.stream().map(X -> X.toDoc()).collect(Collectors.toList());

        if (database.getCollection("currencies").count() > 0)
        database.getCollection("currencies").deleteMany(new BasicDBObject());

        database.getCollection("currencies")
                .insertMany(docList);

        mongo.close();
    }

    MongoClient mongo;
    MongoDatabase database;

    public void connect() {
        mongo = new MongoClient( host , port );

        MongoCredential credential;
        credential = MongoCredential.createCredential(user, db_name, password.toCharArray());
        System.out.println("[MongoDB] Connected to the database successfully.");

        database = mongo.getDatabase(db_name);
        System.out.println("Credentials :: "+ credential);
    }




}
