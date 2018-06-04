package REST;

import Resources.Currencies;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class AppService extends Application<AppConfig> {

    public AppService() {
    }

    public static void main(String[] args) throws Exception {
        (new AppService()).run(args);
    }

    @Override
    public String getName() {
        return "Currencies server";
    }

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {
        final Currencies currencies = new Currencies();
        environment.jersey().register(currencies);
    }
}
