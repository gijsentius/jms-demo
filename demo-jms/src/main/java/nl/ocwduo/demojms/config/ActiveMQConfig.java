package nl.ocwduo.demojms.config;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import javax.jms.ConnectionFactory;

public class ActiveMQConfig {

    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Bean
    @Qualifier("activemqConnectionFactory")
    public ConnectionFactory jmsConnectionFactory() {
        return new ActiveMQConnectionFactory(brokerUrl);
    }
}
