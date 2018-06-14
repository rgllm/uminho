package Data;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Page {

    private long totalCurrencies;
    private long totalPages;
    private int page;
    private int perPage;
    private List<Currency> currencies;

    public Page(long totalCurrencies, long totalPages, int page, int perPage, List<Currency> currencies) {
        this.totalCurrencies = totalCurrencies;
        this.totalPages = totalPages;
        this.page = page;
        this.perPage = perPage;
        this.currencies = currencies;
    }

    @JsonProperty
    public long getTotalCurrencies() {
        return totalCurrencies;
    }

    @JsonProperty
    public long getTotalPages() {
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
    public List<Currency> getCurrencies() {
        return currencies;
    }
}
