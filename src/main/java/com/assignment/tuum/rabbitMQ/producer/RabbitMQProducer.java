package com.assignment.tuum.rabbitMQ.producer;

import com.assignment.tuum.constants.RabbitMQConstants;
import com.assignment.tuum.dtos.ReturnAccountDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(ReturnAccountDto returnAccountDto)
    {
        rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGE,RabbitMQConstants.BINDING_KEY,returnAccountDto);
    }
}
