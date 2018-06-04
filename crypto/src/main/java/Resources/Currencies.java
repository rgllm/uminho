package Resources;

import Data.CurrenciesDB;
import Data.CurrenciesFetch;
import Data.Currency;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/currencies")
@Produces(MediaType.APPLICATION_JSON)
public class Currencies {

    CurrenciesDB db = new CurrenciesDB();

    @GET
    public List<Currency> getAll(@PathParam("page") String page, @PathParam("perPage") String perPage){
        if (page.equals(""))




    }
}


