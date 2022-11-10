package nl.ocwduo.demojms.receiver;

import nl.ocwduo.demojms.model.SimpleMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleReceiver {

    private static final Logger logger = LoggerFactory.getLogger(SimpleReceiver.class);
    private static final String QUEUE_NAME = "DEV.QUEUE.1";

//    Active mq listeners
// Bij active mq wordt er per listener 1 connection gebruikt
//    @JmsListener(destination = "test", containerFactory = "pooledJmsListenerContainerFactory", concurrency = "100-300")
//    public void receiveMessageTest(SimpleMessage message) {
//        logger.info(message.toString());
//    }
//
//    @JmsListener(destination = "test", containerFactory = "pooledJmsListenerContainerFactory", concurrency = "100-300")
//    public void receiveMessageTest2(SimpleMessage message) {
//        logger.info(message.toString());
//    }

    // Bij ibm mq wordt er per listener 1 connection gedeeld
    @JmsListener(destination = QUEUE_NAME, containerFactory = "pooledJmsListenerContainerFactory", concurrency = "20-100")
    public void receiveMessageTest(SimpleMessage message) {
        logger.info(message.toString());
    }

    @JmsListener(destination = QUEUE_NAME, containerFactory = "pooledJmsListenerContainerFactory", concurrency = "20-100")
    public void receiveMessageTest2(SimpleMessage message) {
        logger.info(message.toString());
    }
}
