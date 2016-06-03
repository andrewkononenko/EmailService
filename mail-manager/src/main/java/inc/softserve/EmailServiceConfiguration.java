package inc.softserve;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class EmailServiceConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String sendEmailUrl;

    @NotEmpty
    @JsonProperty
    private String sendEmailPath;

    @NotEmpty
    @JsonProperty
    private String espConnectorAdmin;

    @NotEmpty
    @JsonProperty
    private String espConnectorHealth;

    @NotEmpty
    @JsonProperty
    private String activeMQPath;

    @NotNull
    @Valid
    private MongoConfiguration mongoConf;

    @NotNull
    private String queueName;

    @NotNull
    private Integer maxReconnectCount;

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

    public String getEspConnectorAdmin() {
        return espConnectorAdmin;
    }

    public void setEspConnectorAdmin(String espConnectorAdmin) {
        this.espConnectorAdmin = espConnectorAdmin;
    }

    public String getEspConnectorHealth() {
        return espConnectorHealth;
    }

    public void setEspConnectorHealth(String espConnectorHealth) {
        this.espConnectorHealth = espConnectorHealth;
    }

    public MongoConfiguration getMongoConf() {
        return mongoConf;
    }

    public void setMongoConf(MongoConfiguration mongoConf) {
        this.mongoConf = mongoConf;
    }

    public String getActiveMQPath() {
        return activeMQPath;
    }

    public void setActiveMQPath(String activeMQPath) {
        this.activeMQPath = activeMQPath;
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Integer getMaxReconnectCount() {
        return maxReconnectCount;
    }

    public void setMaxReconnectCount(Integer maxReconnectCount) {
        this.maxReconnectCount = maxReconnectCount;
    }
}
