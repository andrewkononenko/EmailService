package inc.softserve;

import inc.softserve.common.EnvelopeTools;
import inc.softserve.dao.EnvelopeDao;
import inc.softserve.dao.UserDao;
import inc.softserve.services.EnvelopeService;
import inc.softserve.services.EnvelopeServiceImpl;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnvelopeServiceTest extends Assert {

    @Mock private UserDao usrDao;
    @Mock private EnvelopeDao envDao;
    @Mock private EnvelopeTools envTools;
    @InjectMocks private EnvelopeServiceImpl service;

    @BeforeClass
    public void setUpDataSource() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @DataProvider(name = "saveOrUpdateData")
    public Object[][] saveOrUpdateData() {
        return new Object[][]{
                {EnvelopeState.SENT},
                {EnvelopeState.IN_PROGRESS},
                {EnvelopeState.ERROR},
        };
    }

    @Test(dataProvider = "saveOrUpdateData")
    public void testSaveOrUpdate(EnvelopeState expected) throws Exception {
        User user = new User("testUser", "testName", "testSurname");

        when(usrDao.getUserByUsername("testUser")).thenReturn(user);
        when(envDao.saveOrUpdate(any(Envelope.class))).thenAnswer(args -> args.getArguments()[0]); //returns same object passed to saveOrUpdate() method
        when(envTools.sendEnvelope(any(Envelope.class))).thenReturn(expected);

        Envelope actual = service.saveOrUpdate("TestEmail", "testUser22@example.com", "testUser", "Some template");
        assertEquals(actual.getState(), expected);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testSaveOrUpdateWithBadUsername() {
        when(usrDao.getUserByUsername(anyString())).thenReturn(null);
        service.saveOrUpdate("TestEmail", "testUser22@example.com", "testUser22", "Some template");
    }

    @Test
    public void testGetEnvelopeStateByBadId() {
        when(envDao.getById("badId")).thenReturn(null);
        EnvelopeState badIdState = service.getEnvelopeStateById("badId");
        assertEquals(badIdState, EnvelopeState.NOT_FOUND);
    }

    @Test
    public void testGetEnvelopeStateByGoodId() {
        User user = new User("testUser", "a", "b");
        Envelope envelopeSent = new Envelope(user, "testUser22@example.com", "TestEmail", "Some template", EnvelopeState.SENT);
        when(envDao.getById("goodId")).thenReturn(envelopeSent);
        EnvelopeState goodIdState = service.getEnvelopeStateById("goodId");
        assertEquals(goodIdState, EnvelopeState.SENT);
    }
}
