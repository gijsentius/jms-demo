package com.ocwduo.demojmssender.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.JmsPoolConnectionFactoryFactory;
import org.springframework.boot.autoconfigure.jms.JmsPoolConnectionFactoryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;

import javax.jms.ConnectionFactory;
import javax.jms.JMSException;

@Configuration
public class JmsConfig {
    @Value("${jms.max.PoolSize}")
    private int maxPoolSize;

    @Value("${jms.max.sessionsPerConnection}")
    private int maxSessionsPerConnection;

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }

    @Bean
    public ConnectionFactory pooledConnectionFactory(
            JmsPoolConnectionFactoryFactory jmsPoolConnectionFactoryFactory,
            @Qualifier("ibmqqConnectionFactory") ConnectionFactory connectionFactory) {
        return jmsPoolConnectionFactoryFactory.createPooledConnectionFactory(connectionFactory);
    }

    @Bean
    public JmsPoolConnectionFactoryFactory jmsPoolConnectionFactoryFactory(JmsPoolConnectionFactoryProperties jmsPoolConnectionFactoryProperties) {
        return new JmsPoolConnectionFactoryFactory(jmsPoolConnectionFactoryProperties);
    }

    @Bean
    public JmsPoolConnectionFactoryProperties jmsPoolConnectionFactoryProperties() {
        var properties = new JmsPoolConnectionFactoryProperties();
        properties.setMaxConnections(maxPoolSize);
        properties.setMaxSessionsPerConnection(maxSessionsPerConnection);
        return properties;
    }

    @Bean
    @Qualifier("JmsTemplate")
    public JmsTemplate jmsTemplate(ConnectionFactory pooledConnectionFactory) throws JMSException {
        return new JmsTemplate(pooledConnectionFactory);
    }
}
s