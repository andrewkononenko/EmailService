package inc.softserve.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import inc.softserve.EmailServiceApplication;

/**
 * Created by Andrew on 10.05.2016.
 */
public class Envelope {
    private Long id;
    private User from;
    private String to;
    private String subject;
    private String template;
    private String state;

    public Envelope(User from, String to, String subject, String template, String state) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.template = template;
        this.state = state;
    }

    public Envelope(String username, String to, String subject, String template) {
        this.from = EmailServiceApplication.users.get(username);
        this.to = to;
        this.subject = subject;
        this.template = template;
        this.state = "In progress";
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty
    public User getFrom() {
        return from;
    }

    public void setFrom(User from) {
        this.from = from;
    }

    @JsonProperty
    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @JsonProperty
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @JsonProperty
    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    @JsonProperty
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
