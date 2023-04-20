package com.assignment.tuum.rabbitMQ.config;

import com.assignment.tuum.constants.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {


    @Bean
    public TopicExchange exchange()
    {
        return new TopicExchange(RabbitMQConstants.EXCHANGE);
    }

    @Bean
    public Queue jsonQueue()
    {
        return new Queue(RabbitMQConstants.QUEUE);
    }

    @Bean
    public Binding bindingJsonQueue()
    {
        return BindingBuilder.bind(jsonQueue())
                .to(exchange())
                .with(RabbitMQConstants.BINDING_KEY);
    }

    @Bean
    public MessageConverter converter()
    {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory)
    {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return  rabbitTemplate;
    }
}
