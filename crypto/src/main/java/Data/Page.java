package Data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Page {

    private int totalCurrencies;
    private int totalPages;
    private int page;
    private int perPage;
    private ArrayList<Currency> currencies;

    public Page(int totalCurrencies, int totalPages, int page, int perPage, ArrayList<Currency> currencies) {
        this.totalCurrencies = totalCurrencies;
        this.totalPages = totalPages;
        this.page = page;
        this.perPage = perPage;
        this.currencies = currencies;
    }

    @JsonProperty
    public int getTotalCurrencies() {
        return totalCurrencies;
    }

    @JsonProperty
    public int getTotalPages() {
        return totalPages;
    }

    @JsonProperty
    public int getPage() {
        return page;
    }

    @JsonProperty
    public int getPerPage() {
        return perPage;
    }

    @JsonProperty
    public ArrayList<Currency> getCurrencies() {
        return currencies;
    }
}
