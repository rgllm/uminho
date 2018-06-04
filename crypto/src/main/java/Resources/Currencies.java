package Resources;

import Data.CurrenciesDB;
import Data.CurrenciesFetch;
import Data.Currency;
import Data.Page;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/currencies")
@Produces(MediaType.APPLICATION_JSON)
public class Currencies {

    //CurrenciesDB db = new CurrenciesDB();

    private Page newPage(int page){
        List<Currency> list = CurrenciesFetch.parseJson(CurrenciesFetch.fetchJson("https://api.coinmarketcap.com/v2/ticker/"));
        return new Page(list.size(), list.size()/20, page, 20, list.subList((page-1)*20,page*20));
    }



    @GET
    public Page getAll(@DefaultValue("-1") @QueryParam("page") int page, @DefaultValue("-1") @QueryParam("perPage") int perPage){
        if (page == -1)
            return newPage(1);
        else
            return newPage(page);

    }

    @Path("/{id}")
    @GET
    public Currency getCurrency(@PathParam("id") String id){
        return
                CurrenciesFetch.parseJson(CurrenciesFetch.fetchJson("https://api.coinmarketcap.com/v2/ticker/"))
                .stream().filter(X -> X.getName().toLowerCase().equals(id) == true).collect(Collectors.toList()).get(0);
    }
}


