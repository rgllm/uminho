package Data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.*;


public class CurrenciesFetch implements Runnable {

    private String db_url;

    public CurrenciesFetch(String db_url) {
        this.db_url = db_url;
    }

    public static String fetchJson(String db_url){
        StringBuilder sb = new StringBuilder();

        try {
            InputStream is = new URL(db_url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
        } catch(Exception e) {e.printStackTrace();}

        return sb.toString();
    }

    public static List<Currency> parseJson(String json){
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();

        JsonParser parser = new JsonParser();
        JsonObject rootObj = parser.parse(json).getAsJsonObject();
        JsonObject data = rootObj.getAsJsonObject("data");

        List<Currency> list = new ArrayList<Currency>();

        for(Map.Entry<String, JsonElement> entry : data.entrySet()) {
            JsonObject object = (JsonObject) entry.getValue();
            JsonObject objectQ = (JsonObject) ((JsonObject) object.get("quotes")).get("USD");

            list.add(new Currency(
                    object.get("id").getAsString(),
                    object.get("name").getAsString(),
                    object.get("symbol").getAsString(),
                    object.get("rank").getAsInt(),
                    objectQ.get("price").isJsonNull() ? new BigDecimal(0) : objectQ.get("price").getAsBigDecimal(),
                    objectQ.get("market_cap").isJsonNull() ? new BigDecimal(0) : objectQ.get("market_cap").getAsBigDecimal(),
                    objectQ.get("percent_change_1h").isJsonNull() ? new BigDecimal(0) : objectQ.get("percent_change_1h").getAsBigDecimal(),
                    objectQ.get("volume_24h").isJsonNull() ? new BigDecimal(0) : objectQ.get("volume_24h").getAsBigDecimal(),
                    object.get("total_supply").isJsonNull() ? 0 : object.get("total_supply").getAsDouble()
            ));
        }

        return list;
    }

    @Override
    public void run() {
        while(true){
            CurrenciesDB.clearDB();

            JsonParser parser = new JsonParser();
            JsonObject rootObj = parser.parse(fetchJson("https://api.coinmarketcap.com/v2/global/")).getAsJsonObject();
            int data = rootObj.getAsJsonObject("data").get("active_cryptocurrencies").getAsInt();

            int i = 1;
            List<Currency> list = new ArrayList<>();
            while(i <= data){
                list.addAll(
                    parseJson(
                        fetchJson("https://api.coinmarketcap.com/v2/ticker/?start=" + i + "&limit=100")));
                i+=100;
            }

            CurrenciesDB.insertDB(list);

            try {
                Thread.sleep(1000 * 60);
            } catch (InterruptedException e) {e.printStackTrace();}
        }


    }

}
