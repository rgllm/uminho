package Data;

import Resources.Currencies;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CurrenciesDB {

    MongoClient mongo;
    MongoDatabase database;

    public CurrenciesDB() {
        MongoCredential credential = MongoCredential.createCredential("trding", "trding_users", "trding2018".toCharArray());
        mongo = new MongoClient(
                new ServerAddress("ds141720.mlab.com", 41720),
                Arrays.asList(credential)
        );

        System.out.println("Credentials :: "+ credential);
        System.out.println("[MongoDB] Connected to the database successfully.");
        database = mongo.getDatabase("trding_users");

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

    public List<Currencies> getCrypto() {
        FindIterable<Document> cur = database.getCollection("currencies").find();
        List<Currencies> list = new ArrayList<>();
        for (Document doc: cur)
            list.add(
                    new Currency(doc.getString("_id"))
            )


    }

}
