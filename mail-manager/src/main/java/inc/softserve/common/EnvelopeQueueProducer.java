package inc.softserve.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import inc.softserve.Envelope;
import inc.softserve.EnvelopeState;
import inc.softserve.annotations.MQPath;
import inc.softserve.annotations.MaxReconnectCount;
import inc.softserve.annotations.QueueName;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.util.concurrent.locks.ReentrantLock;

//TODO: add tests
public class EnvelopeQueueProducer {
    ActiveMQConnectionFactory connectionFactory;
    private ObjectMapper objectMapper;
    private Session session;
    private Connection connection;
    private int reconnectCount = 0;
    private boolean queueIsAlive = true;
    private ReentrantLock lock = new ReentrantLock();
    private Integer maxReconnect;
    private String queueName;

    @Inject
    public EnvelopeQueueProducer(@MQPath String activeMQPath,
                                 ObjectMapper objectMapper,
                                 @MaxReconnectCount Integer maxReconnectCount,
                                 @QueueName String queueName) {
        this.connectionFactory = new ActiveMQConnectionFactory(activeMQPath);
        this.objectMapper = objectMapper;
        this.maxReconnect = maxReconnectCount;
        this.queueName = queueName;
        initSession();
    }

    private void initSession() {
        try {
            this.connection = connectionFactory.createConnection();
            connection.start();
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public EnvelopeState send(Envelope envelope) {
        if (queueIsAlive) {
            try {
                return sendOk(envelope);
            } catch (JMSException e) {
                tryReconnect();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            return EnvelopeState.ERROR;
        }
        return send(envelope);
    }

    private EnvelopeState sendOk(Envelope envelope) throws JMSException, JsonProcessingException {
        Destination destination = session.createQueue(queueName);
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        TextMessage message = session.createTextMessage(objectMapper.writeValueAsString(envelope));

        producer.send(message);
        return EnvelopeState.IN_QUEUE;
    }

    private void reconnect() {
        try {
            if (reconnectCount < maxReconnect) {
                reconnectCount++;
                Thread.sleep(15000);
                this.connection = connectionFactory.createConnection();
                connection.start();
                this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                reconnectCount = 0;
            } else {
                queueIsAlive = false;
            }
        } catch (JMSException e) {
            reconnect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tryReconnect() {
        try {
            boolean isConnectionLocked = lock.tryLock();
            if (isConnectionLocked) {
                reconnect();
                lock.unlock();
            } else {
                while(lock.isLocked()) {}
            }
        } catch (Throwable e) {
            //Theoretically never get here, but in case do unlock()
            e.printStackTrace();
            lock.unlock();
        }
    }
}
