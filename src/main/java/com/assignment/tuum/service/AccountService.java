package com.assignment.tuum.service;

import com.assignment.tuum.dtos.CreateAccountDto;
import com.assignment.tuum.dtos.ReturnAccountDto;
import com.assignment.tuum.exceptions.UserNotFoundException;
import com.assignment.tuum.mapper.AccountMapper;
import com.assignment.tuum.model.Account;
import com.assignment.tuum.model.Balance;
import com.assignment.tuum.rabbitMQ.producer.RabbitMQProducer;
import com.assignment.tuum.repository.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    private RabbitMQProducer producer;

    public AccountService (RabbitMQProducer producer)
    {
        this.producer = producer;
    }

    public ReturnAccountDto createAccount(CreateAccountDto accountDto) throws HttpMessageNotReadableException
    {
        Account account = accountMapper.toEntity(accountDto);

        for(Balance b: accountDto.getBalances())
            b.setAccount(account);

        Account savedAccount = accountRepository.save(account);
        ReturnAccountDto returnAccountDto = accountMapper.toReturnAccountDto(savedAccount);

        producer.sendMessage(returnAccountDto);   //RabbitMQ message producer

        return returnAccountDto;
    }

    public CreateAccountDto getAccountDetails(Long id)
    {
        Account account = accountRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Account With Id #" + id + " not found "));
        return accountMapper.toDto(account);
    }
}

