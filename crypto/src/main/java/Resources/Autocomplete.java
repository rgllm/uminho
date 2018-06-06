package Resources;


import Data.CurrenciesDB;
import Data.Currency;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Path("/autocomplete")
@Produces(MediaType.APPLICATION_JSON)
public class Autocomplete {

    CurrenciesDB db;
    public Autocomplete() {
        this.db = new CurrenciesDB();
    }

    @GET
    public List<Currency> search(@DefaultValue("") @QueryParam("searchQuery") String word){
        List<Currency> list = db.resultsTo(word);
        Collections.sort(list);
        return list;
    }
}
