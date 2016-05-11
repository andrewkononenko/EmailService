package inc.softserve.model;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Andrew on 10.05.2016.
 */
public class Envelope {
    Long id;
    User from;
    String to;
    String subject;
    String template;
    String state;

    public Envelope() {}

    public Envelope(Long id, User from, String to, String subject, String template, String state) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.template = template;
        this.state = state;
    }

    @JsonProperty
    public Long getId() {
        return id;
    }

    public void setId(Long id) {this.id = id;}

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
