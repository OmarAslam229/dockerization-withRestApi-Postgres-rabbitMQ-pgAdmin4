package com.assignment.tuum.rabbitMQ.producer;

import com.assignment.tuum.constants.RabbitMQConstants;
import com.assignment.tuum.dtos.ReturnAccountDto;
import com.assignment.tuum.dtos.TransactionDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private RabbitTemplate rabbitTemplate;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate)
    {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessageToAccountQueue(ReturnAccountDto returnAccountDto)
    {
        rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGE,RabbitMQConstants.BINDING_KEY_ACCOUNT,returnAccountDto);
    }

    public void sendMessageToTransactionQueue(TransactionDto returnTransactionDto)
    {
        rabbitTemplate.convertAndSend(RabbitMQConstants.EXCHANGE,RabbitMQConstants.BINDING_KEY_TRANSACTION,returnTransactionDto);
    }
}
