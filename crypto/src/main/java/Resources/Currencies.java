package Resources;

import Data.CurrenciesDB;
import Data.CurrenciesFetch;
import Data.Currency;
import Data.Page;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/cryptocurrencies")
@Produces(MediaType.APPLICATION_JSON)
public class Currencies {

    CurrenciesDB db;

    public Currencies() {
        this.db = new CurrenciesDB();
    }

    private Page newPage(int page){
        List<Currency> list = db.getCrypto((page-1)*20, page*20);
        long size = db.getSize();
        return new Page(size, size/20, page, 20, list);
    }

    @GET
    public Page getAll(@DefaultValue("-1") @QueryParam("page") int page, @DefaultValue("-1") @QueryParam("perPage") int perPage){
        if (page == -1)
            return newPage(1);
        else
            return newPage(page);

    }


}


