package inc.softserve;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

public class EmailServiceConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    String sendEmailUrl;

    @NotEmpty
    @JsonProperty
    String sendEmailPath;

    @NotEmpty
    @JsonProperty
    String ESPConnectorAdmin;

    @NotEmpty
    @JsonProperty
    String ESPConnectorHealth;

    public String getSendEmailUrl() {
        return sendEmailUrl;
    }

    public void setSendEmailUrl(String sendEmailUrl) {
        this.sendEmailUrl = sendEmailUrl;
    }

    public String getSendEmailPath() {
        return sendEmailPath;
    }

    public void setSendEmailPath(String sendEmailPath) {
        this.sendEmailPath = sendEmailPath;
    }

    public String getESPConnectorAdmin() {
        return ESPConnectorAdmin;
    }

    public void setESPConnectorAdmin(String ESPConnectorAdmin) {
        this.ESPConnectorAdmin = ESPConnectorAdmin;
    }

    public String getESPConnectorHealth() {
        return ESPConnectorHealth;
    }

    public void setESPConnectorHealth(String ESPConnectorHealth) {
        this.ESPConnectorHealth = ESPConnectorHealth;
    }
}
