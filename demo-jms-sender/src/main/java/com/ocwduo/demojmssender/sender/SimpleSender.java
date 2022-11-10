package com.ocwduo.demojmssender.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

public class SimpleSender implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSender.class);

    private JmsTemplate jmsTemplate;
    private String queueName;

    public SimpleSender(JmsTemplate jmsTemplate, String queueName) {
        this.jmsTemplate = jmsTemplate;
        this.queueName = queueName;
    }

    @Override
    public void run() {
        logger.info("Sending a message...");
        this.jmsTemplate.send(queueName, session -> session.createTextMessage("test"));
    }
}
