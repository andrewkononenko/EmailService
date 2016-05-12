package inc.softserve;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Andrew on 11.05.2016.
 */
public enum EnvelopeState {
    IN_PROGRESS,
    SENT,
    IN_QUEUE,
    ERROR;
}
