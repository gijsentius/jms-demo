package com.ocwduo.demojmssender.sender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SimpleSenderExecutorService {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSenderExecutorService.class);

    private JmsTemplate jmsTemplate;

    @Value("${queue.name}")
    private String queueName;

    @Autowired
    SimpleSenderExecutorService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Autowired
    public void execute() {
        ExecutorService executor = Executors.newFixedThreadPool(25);
        for (int i = 0; i < 10000000; i++) {
            Runnable worker = new SimpleSender(jmsTemplate, queueName);
            executor.execute(worker);
        }
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        logger.info("Finished all threads");
    }
}
