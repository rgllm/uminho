package Resources;

import Data.CurrenciesFetch;
import Data.Currency;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/currencies")
@Produces(MediaType.APPLICATION_JSON)
public class Currencies {
    @GET
    public List<Currency> getAll(){
        return CurrenciesFetch.parseJson(CurrenciesFetch.fetchJson("https://api.coinmarketcap.com/v2/ticker/"));
    }
}


