package inc.softserve;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Created by Andrew on 10.05.2016.
 */
public class EmailServiceConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    String sendEmailUrl;

    @NotEmpty
    @JsonProperty
    String sendEmailPath;

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
}
