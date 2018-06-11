package REST;

import Data.CurrenciesDB;
import Data.CurrenciesFetch;
import Data.Currency;
import Resources.Autocomplete;
import Resources.Currencies;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;
import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;



public class AppService extends Application<AppConfig> {

    public AppService() {
    }

    public static void main(String[] args) throws Exception {
        (new Thread(new CurrenciesFetch("https://api.coinmarketcap.com/v2/ticker/"))).start();
        (new AppService()).run(args);

    }

    @Override
    public String getName() {
        return "Currencies server";
    }

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter("allowedOrigins", "*");
        cors.setInitParameter("allowedHeaders", "X-Requested-With,Content-Type,Accept,Origin");
        cors.setInitParameter("allowedMethods", "GET");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

	final Currencies currencies = new Currencies();
        final Autocomplete ac = new Autocomplete();
        final Resources.Currency c = new Resources.Currency();
        environment.jersey().register(currencies);
        environment.jersey().register(ac);
        environment.jersey().register(c);
    }
}
