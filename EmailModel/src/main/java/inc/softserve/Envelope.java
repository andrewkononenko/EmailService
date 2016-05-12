package inc.softserve;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Envelope {
    private Long id;
    private User from;
    private String to;
    private String subject;
    private String template;
    private EnvelopeState state;

    public Envelope(User from, String to, String subject, String template, EnvelopeState state) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.template = template;
        this.state = state;
    }

    public Envelope(){}

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
    public EnvelopeState getState() {
        return state;
    }

    public void setState(EnvelopeState state) {
        this.state = state;
    }
}
