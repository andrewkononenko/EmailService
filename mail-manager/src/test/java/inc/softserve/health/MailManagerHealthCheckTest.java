package inc.softserve.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import inc.softserve.common.EnvelopeTools;
import inc.softserve.dao.MongoManaged;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MailManagerHealthCheckTest extends Assert {

    private MailManagerHealthCheck healthCheck;
    private EnvelopeTools envTools;
    private MongoManaged mongo;
    private final String HEALTHY_ESP_MESSAGE = "{\"deadlocks\":{\"healthy\":true},\"health\":{\"healthy\":true}}";

    @BeforeClass
    public void setUpDataSource() throws Exception {
        envTools = mock(EnvelopeTools.class);
        mongo = mock(MongoManaged.class);
        this.healthCheck = new MailManagerHealthCheck("","", envTools, mongo);
    }

    @Test
    public void testCheckHealthy() {
        DB dbMock = mock(DB.class);
        CommandResult statsResultMock = mock(CommandResult.class);
        when(mongo.getDb()).thenReturn(dbMock);
        when(dbMock.getStats()).thenReturn(statsResultMock);
        doReturn(HEALTHY_ESP_MESSAGE).when(envTools).sendGetRequest(anyString(),anyString());
        assertEquals(healthCheck.check(), HealthCheck.Result.healthy());
    }

    @Test
    public void testCheckMongoException() {
        DB dbMock = mock(DB.class);
        when(mongo.getDb()).thenReturn(dbMock);
        when(dbMock.getStats()).thenThrow(new RuntimeException());
        when(envTools.sendGetRequest(anyString(),anyString())).thenReturn(HEALTHY_ESP_MESSAGE);
        assertEquals(healthCheck.check(), HealthCheck.Result.unhealthy("Service unavailable!"));
    }

    @Test
    public void testCheckMongoDelay() {
        DB dbMock = mock(DB.class);
        CommandResult statsResultMock = mock(CommandResult.class);
        when(mongo.getDb()).thenReturn(dbMock);
        when(dbMock.getStats()).thenAnswer(args -> {TimeUnit.SECONDS.sleep(1); return statsResultMock;});
        when(envTools.sendGetRequest(anyString(),anyString())).thenReturn(HEALTHY_ESP_MESSAGE);
        assertEquals(healthCheck.check(), HealthCheck.Result.unhealthy("Service unavailable!"));
    }

    @Test
    public void testCheckEspConnectorException() {
        DB dbMock = mock(DB.class);
        CommandResult statsResultMock = mock(CommandResult.class);
        when(mongo.getDb()).thenReturn(dbMock);
        when(dbMock.getStats()).thenReturn(statsResultMock);
        when(envTools.sendGetRequest(anyString(),anyString())).thenThrow(new ArrayIndexOutOfBoundsException());
        assertEquals(healthCheck.check(), HealthCheck.Result.unhealthy("Service unavailable!"));
    }

    @Test
    public void testCheckEspConnectorDelay() {
        DB dbMock = mock(DB.class);
        CommandResult statsResultMock = mock(CommandResult.class);
        when(mongo.getDb()).thenReturn(dbMock);
        when(dbMock.getStats()).thenReturn(statsResultMock);
        when(envTools.sendGetRequest(anyString(),anyString())).thenAnswer(args -> {TimeUnit.SECONDS.sleep(2);
                                                                                return HEALTHY_ESP_MESSAGE;});
        assertEquals(healthCheck.check(), HealthCheck.Result.unhealthy("Service unavailable!"));
    }
}
