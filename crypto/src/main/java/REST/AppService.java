package REST;

import Data.CurrenciesDB;
import Data.CurrenciesFetch;
import Data.Currency;
import Resources.Autocomplete;
import Resources.Currencies;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class AppService extends Application<AppConfig> {

    public AppService() {
    }

    public static void main(String[] args) throws Exception {
        //(new CurrenciesFetch("https://api.coinmarketcap.com/v2/ticker/")).run();
        System.out.println("adasdA AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        (new AppService()).run(args);
    }

    @Override
    public String getName() {
        return "Currencies server";
    }

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {
        final Currencies currencies = new Currencies();
        final Autocomplete ac = new Autocomplete();
        final Resources.Currency c = new Resources.Currency();
        environment.jersey().register(currencies);
        environment.jersey().register(ac);
        environment.jersey().register(c);


    }
}
