package com.funkyjester.demo.integration.config.profile;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.artemis.core.server.ActiveMQComponent;
import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.apache.activemq.artemis.jms.client.ActiveMQJMSConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.component.jms.JmsConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import javax.annotation.PostConstruct;
import javax.jms.ConnectionFactory;

@Configuration
@Getter @Setter
@Profile("local")
@Slf4j
public class AppLocalConfig {
    @Value("${jms.connection.url}")
    String jmsConnectionUrl;
    @Value("${spring.artemis.user}")
    String jmsUser;
    @Value("${spring.artemis.password}")
    String jmsPassword;

    @Autowired
    CamelContext camelContext;

    /********************** JMS **********************/

    @Bean
    public JmsComponent jms() {
        JmsComponent jmsComponent = new JmsComponent();
        JmsConfiguration configuration = jmsComponent.getConfiguration();
        configuration.setConnectionFactory(new ActiveMQJMSConnectionFactory(jmsConnectionUrl, jmsUser, jmsPassword));
        return jmsComponent;
    }
}
