package nl.ocwduo.demojms.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.jms.JmsPoolConnectionFactoryFactory;
import org.springframework.boot.autoconfigure.jms.JmsPoolConnectionFactoryProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;

import javax.jms.ConnectionFactory;

@Configuration
public class JmsConfiguration {

    @Value("${jms.max.PoolSize}")
    private int maxPoolSize;

    @Value("${jms.max.sessionsPerConnection}")
    private int maxSessionsPerConnection;

    @Bean
    public DefaultJmsListenerContainerFactory pooledJmsListenerContainerFactory(DefaultJmsListenerContainerFactoryConfigurer configurer,
                                                        ConnectionFactory pooledConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, pooledConnectionFactory);
        return factory;
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
}
