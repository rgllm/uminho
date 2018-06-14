package Data;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.bson.Document;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;

public class Currency implements Comparable<Currency>{
    private String id;
    private String name;
    private String symbol;
    private int rank;

    private BigDecimal price;
    private BigDecimal market_cap;
    private BigDecimal percentage24;
    private BigDecimal volume24;
    private Double totalSupply;

    public Currency(String id, String name, String symbol, int rank, BigDecimal price, BigDecimal market_cap, BigDecimal percentage24, BigDecimal volume24, Double totalSupply) {
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.rank = rank;
        this.price = price;
        this.market_cap = market_cap;
        this.percentage24 = percentage24;
        this.volume24 = volume24;
        this.totalSupply = totalSupply;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", rank=" + rank +
                ", price=" + price +
                ", market_cap=" + market_cap +
                ", percentage24=" + percentage24 +
                ", volume24=" + volume24 +
                ", totalSupply=" + totalSupply +
                '}';
    }

    public Document toDoc(){
        return new Document()
                .append("_id", id)
                .append("name", name)
                .append("symbol", symbol)
                .append("rank", rank)
                .append("price", price.toString())
                .append("market_cap", market_cap.toString())
                .append("percentage24", percentage24.toString())
                .append("volume24", volume24.toString())
                .append("totalSupply", totalSupply);
    }

    @JsonProperty
    public String getId() {
        return id;
    }

    @JsonProperty
    public String getName() {
        return name;
    }

    @JsonProperty
    public String getSymbol() {
        return symbol;
    }

    @JsonProperty
    public int getRank() {
        return rank;
    }

    @JsonProperty
    public BigDecimal getPrice() {
        return price;
    }

    @JsonProperty
    public BigDecimal getMarket_cap() {
        return market_cap;
    }

    @JsonProperty
    public BigDecimal getPercentage24() {
        return percentage24;
    }

    @JsonProperty
    public BigDecimal getVolume24() {
        return volume24;
    }

    @JsonProperty
    public Double getTotalSupply() {
        return totalSupply;
    }

    @Override
    public int compareTo(Currency currency) {
        return this.rank - currency.getRank();
    }
}

