package inc.softserve.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import inc.softserve.common.EnvelopeTools;
import inc.softserve.dao.MongoManaged;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MailManagerHealthCheckTest extends Assert {

    @Mock private EnvelopeTools envTools;
    @Mock private MongoManaged mongo;
    @Mock private DB dbMock;
    @InjectMocks private MailManagerHealthCheck healthCheck;

    private final String HEALTHY_ESP_MESSAGE = "{\"deadlocks\":{\"healthy\":true},\"health\":{\"healthy\":true}}";

    @BeforeClass
    public void setUpDataSource() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(mongo.getDb()).thenReturn(dbMock);
    }

    @Test(priority = 1)
    public void testCheckHealthy() {
        CommandResult statsResultMock = mock(CommandResult.class);
        when(dbMock.getStats()).thenReturn(statsResultMock);
        doReturn(HEALTHY_ESP_MESSAGE).when(envTools).sendGetRequest(anyString(), anyString());
        assertEquals(healthCheck.check(), HealthCheck.Result.healthy());
    }

    @Test(priority = 2)
    public void testCheckMongoDelay() {
        CommandResult statsResultMock = mock(CommandResult.class);
        when(dbMock.getStats()).thenAnswer(args -> {
            TimeUnit.SECONDS.sleep(1);
            return statsResultMock;
        });
        when(envTools.sendGetRequest(anyString(), anyString())).thenReturn(HEALTHY_ESP_MESSAGE);
        assertEquals(healthCheck.check(), HealthCheck.Result.unhealthy("Service unavailable!"));
    }

    @Test(priority = 3)
    public void testCheckMongoLowDelay() {
        CommandResult statsResultMock = mock(CommandResult.class);
        when(dbMock.getStats()).thenAnswer(args -> {
            TimeUnit.MILLISECONDS.sleep(400);
            return statsResultMock;
        });
        when(envTools.sendGetRequest(anyString(), anyString())).thenReturn(HEALTHY_ESP_MESSAGE);
        assertEquals(healthCheck.check(), HealthCheck.Result.healthy());
    }

    @Test(priority = 4)
    public void testCheckEspConnectorDelay() {
        CommandResult statsResultMock = mock(CommandResult.class);
        when(dbMock.getStats()).thenReturn(statsResultMock);
        when(envTools.sendGetRequest(anyString(), anyString())).thenAnswer(args -> {
            TimeUnit.SECONDS.sleep(2);
            return HEALTHY_ESP_MESSAGE;
        });
        assertEquals(healthCheck.check(), HealthCheck.Result.unhealthy("Service unavailable!"));
    }

    @Test(priority = 5)
    public void testCheckEspConnectorLowDelay() {
        CommandResult statsResultMock = mock(CommandResult.class);
        when(dbMock.getStats()).thenReturn(statsResultMock);
        doAnswer(args -> {
            TimeUnit.MILLISECONDS.sleep(400);
            return HEALTHY_ESP_MESSAGE;
        })
                .when(envTools).sendGetRequest(anyString(), anyString());
        assertEquals(healthCheck.check(), HealthCheck.Result.healthy());
    }

    @Test(priority = 6)
    public void testCheckMongoException() {
        when(dbMock.getStats()).thenThrow(new RuntimeException());
        when(envTools.sendGetRequest(anyString(), anyString())).thenReturn(HEALTHY_ESP_MESSAGE);
        assertEquals(healthCheck.check(), HealthCheck.Result.unhealthy("Service unavailable!"));
    }

    @Test(priority = 7)
    public void testCheckEspConnectorException() {
        CommandResult statsResultMock = mock(CommandResult.class);
        doReturn(statsResultMock).when(dbMock).getStats();
        when(envTools.sendGetRequest(anyString(), anyString())).thenThrow(new RuntimeException());
        assertEquals(healthCheck.check(), HealthCheck.Result.unhealthy("Service unavailable!"));
    }
}
