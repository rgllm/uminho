package Resources;

import Data.CurrenciesDB;
import Data.CurrenciesFetch;
import Data.Page;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.stream.Collectors;

@Path("/cryptocurrency")
@Produces(MediaType.APPLICATION_JSON)
public class Currency {
    CurrenciesDB db;

    public Currency() {
        this.db = new CurrenciesDB();
    }

    @GET
    @Path("/{id}")
    public Data.Currency getCurrency(@PathParam("id") int id){
        return db.getCryptoID(id);
    }


}
