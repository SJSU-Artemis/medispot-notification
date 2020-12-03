package com.medispot.notification.configurations;

import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadArgumentResolver;
import org.springframework.stereotype.Component;

import static java.util.Collections.singletonList;

@Component
public class SQSConfig {
    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory(MessageConverter messageConverter) {

        QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
        factory.setArgumentResolvers(singletonList(new PayloadArgumentResolver(messageConverter)));
        return factory;
    }

    @Bean
    protected MessageConverter messageConverter(ObjectMapper objectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(objectMapper);
        converter.setSerializedPayloadClass(String.class);
        converter.setStrictContentTypeMatch(false);
        return converter;
    }
    
    @Bean
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(
        AmazonSQSAsync amazonSQSAsync
    ) {
      SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
      factory.setAmazonSqs(amazonSQSAsync);
      factory.setWaitTimeOut(1); // less than 10 sec
      return factory;
    }
}

