import Data.CurrenciesFetch;

public class App {

    public static void main(String[] args) {
        (new CurrenciesFetch("https://api.coinmarketcap.com/v2/ticker/")).run();
    }


}
