package inc.softserve.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Andrew on 11.05.2016.
 */
public enum EnvelopeState {
    IN_PROGRESS("In progress"),
    SENT("Sent"),
    IN_QUEUE("In queue"),
    ERROR("Not sent due to error!");

    private final String value;

    EnvelopeState(String value){
        this.value = value;
    }

    @JsonProperty
    public String getState(){
        return value;
    }

    public static EnvelopeState getNameByCode(String code) {
        for (EnvelopeState s: EnvelopeState.values()) {
            if (code.equals(s.getState()))
                return s;
        }
        return null;
    }

    @Override
    public String toString() {
        return "EnvelopeState{" +
                "value=" + value + '\"' +
                '}';
    }
}
