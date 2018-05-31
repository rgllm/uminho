package Data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
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

    private String fetchJson(){
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

    private List<Currency> parseJson(String json){
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
                    objectQ.get("price").getAsBigDecimal(),
                    objectQ.get("market_cap").getAsBigDecimal(),
                    objectQ.get("percent_change_24h").getAsBigDecimal(),
                    objectQ.get("volume_24h").getAsBigDecimal(),
                    object.get("total_supply").getAsDouble()
            ));
        }

        return list;
    }

    public void run() {
        CurrenciesDB db = new CurrenciesDB("ds141720.mlab.com" ,41720, "trding_users", "trding", "trding2018");
        db.connect();
        db.init((ArrayList<Currency>) parseJson(fetchJson()));
    }




}
