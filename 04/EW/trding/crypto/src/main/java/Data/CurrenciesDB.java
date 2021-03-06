package Data;

import Resources.Currencies;
import com.mongodb.*;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CurrenciesDB {

    private MongoClient mongo;
    private MongoDatabase database;

    public CurrenciesDB() {
        MongoCredential credential = MongoCredential.createCredential("trding", "trding_crypto", "trding2018".toCharArray());
        mongo = new MongoClient(
                new ServerAddress(ADRESS, PORT),
                Arrays.asList(credential)
        );

        System.out.println("Credentials :: "+ credential);
        System.out.println("[MongoDB] Connected to the database successfully.");
        database = mongo.getDatabase("trding_crypto");
    }


    public List<Currency> getCrypto(int from, int to) {
        FindIterable<Document> cur = database.getCollection("currencies")
                .find(new Document("rank", new Document("$gte", from - 1).append("$lte", to )));
        List<Currency> list = new ArrayList<>();
        for (Document doc: cur)
            list.add(
                    new Currency(
                            doc.getString("_id"),
                            doc.getString("name"),
                            doc.getString("symbol"),
                            doc.getInteger("rank"),
                            (new BigDecimal(doc.getString("price").replaceAll(",",""))),
                            (new BigDecimal(doc.getString("market_cap").replaceAll(",",""))),
                            (new BigDecimal(doc.getString("percentage24").replaceAll(",",""))),
                            (new BigDecimal(doc.getString("volume24").replaceAll(",",""))),
                            doc.getDouble("totalSupply")
                    )
            );

        return list;

    }


    //static methods

    public static void clearDB(){
        MongoCredential credential = MongoCredential.createCredential("trding", "trding_crypto", "trding2018".toCharArray());
        MongoClient mongo = new MongoClient(
                new ServerAddress(ADRESS, PORT),
                Arrays.asList(credential)
        );

        System.out.println("Credentials :: "+ credential);
        System.out.println("[MongoDB] Connected to the database successfully.");
        MongoDatabase database = mongo.getDatabase("trding_crypto");

        if (database.getCollection("currencies").count() > 0)
            database.getCollection("currencies").deleteMany(new BasicDBObject());

        mongo.close();
    }

    public static void insertDB(List<Currency> list){
        MongoCredential credential = MongoCredential.createCredential("trding", "trding_crypto", "trding2018".toCharArray());
        MongoClient mongo = new MongoClient(
                new ServerAddress(ADRESS, PORT),
                Arrays.asList(credential)
        );

        System.out.println("Credentials :: "+ credential);
        System.out.println("[MongoDB] Connected to the database successfully.");
        MongoDatabase database = mongo.getDatabase("trding_crypto");

        List<org.bson.Document> docList = list.stream().map(X -> X.toDoc()).collect(Collectors.toList());

        if (database.getCollection("currencies").count() > 0)
            database.getCollection("currencies").deleteMany(new BasicDBObject());

        database.getCollection("currencies")
                .insertMany(docList);

        mongo.close();
    }

    public long getSize() {
        return this.database.getCollection("currencies").count();
    }

    public List<Currency> resultsTo(String word) {
        Document pattern = new Document();
        pattern.put("name", Pattern.compile(word, Pattern.CASE_INSENSITIVE));
        FindIterable<Document> cur = database.getCollection("currencies").find(pattern);

        List<Currency> list = new ArrayList<>();
        for (Document doc: cur)
            list.add(
                    new Currency(
                            doc.getString("_id"),
                            doc.getString("name"),
                            doc.getString("symbol"),
                            doc.getInteger("rank"),
                            (new BigDecimal(doc.getString("price").replaceAll(",",""))),
                            (new BigDecimal(doc.getString("market_cap").replaceAll(",",""))),
                            (new BigDecimal(doc.getString("percentage24").replaceAll(",",""))),
                            (new BigDecimal(doc.getString("volume24").replaceAll(",",""))),
                            doc.getDouble("totalSupply")
                    )
            );
        return list;
    }

    public Currency getCryptoID(int id) {
        Document pattern = new Document();
        pattern.put("_id", id + "");
        FindIterable<Document> cur = database.getCollection("currencies").find(pattern);

        List<Currency> list = new ArrayList<>();
        for (Document doc: cur)
            list.add(
                    new Currency(
                            doc.getString("_id"),
                            doc.getString("name"),
                            doc.getString("symbol"),
                            doc.getInteger("rank"),
                            (new BigDecimal(doc.getString("price").replaceAll(",",""))),
                            (new BigDecimal(doc.getString("market_cap").replaceAll(",",""))),
                            (new BigDecimal(doc.getString("percentage24").replaceAll(",",""))),
                            (new BigDecimal(doc.getString("volume24").replaceAll(",",""))),
                            doc.getDouble("totalSupply")
                    )
            );

        return list.get(0);
    }
}
