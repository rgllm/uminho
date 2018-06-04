package REST;


import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class AppConfig extends Configuration {

    @NotEmpty
    private String port = "8080";

    @JsonProperty
    public String getPort() {
        return this.port;
    }

    @JsonProperty
    public void setPort(String port) {
        this.port = port;
    }
}
