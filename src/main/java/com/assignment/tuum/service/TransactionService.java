package com.assignment.tuum.service;

import com.assignment.tuum.exceptions.IllegalOperations;
import com.assignment.tuum.dtos.TransactionCreateDto;
import com.assignment.tuum.dtos.TransactionDto;
import com.assignment.tuum.dtos.TransactionListDto;
import com.assignment.tuum.exceptions.UserNotFoundException;
import com.assignment.tuum.mapper.TransactionMapper;
import com.assignment.tuum.model.Balance;
import com.assignment.tuum.model.Transaction;
import com.assignment.tuum.model.enums.Direction;
import com.assignment.tuum.rabbitMQ.producer.RabbitMQProducer;
import com.assignment.tuum.repository.BalanceRepository;
import com.assignment.tuum.repository.TransactionRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    private RabbitMQProducer producer;

    public TransactionService (RabbitMQProducer producer)
    {
        this.producer = producer;
    }

    public TransactionDto createTransaction(TransactionCreateDto transactionDto)
    {
        Transaction transaction = transactionMapper.toEntity(transactionDto);

        //Validation Checks ---//////////

        if(transaction.getAmount() <= 0)
            throw new IllegalOperations("Invalid transaction amount : "+ transaction.getAmount());

        if(transaction.getDescription().isEmpty())
            throw new IllegalOperations("Description cannot be null ");

      Balance balance=  balanceRepository.findByAccount_IdAndCurrency(transaction.getAccount().getId(),transaction.getCurrency()).orElseThrow(()
              -> new UserNotFoundException("Can't find account or balance for account With account_Id #" + transaction.getAccount().getId()));

      //performing Transaction //

      Long newBalance = balance.getAvailableBalance();
      if(transaction.getDirection() == Direction.IN)
      {
          newBalance += transaction.getAmount();
          balance.setAvailableBalance(newBalance);
          balanceRepository.save(balance);
      }
      else if (transaction.getDirection() == Direction.OUT)
      {
          if(transaction.getAmount() <= balance.getAvailableBalance())
          {
              newBalance -= transaction.getAmount();
              balance.setAvailableBalance(newBalance);
              balanceRepository.save(balance);
          }
          else   // means user doesn't have sufficient finds to perform this operation
          {
              throw new IllegalOperations("InSufficient funds ");
          }
      }

      transaction.setBalanceAfterTransaction(newBalance);
      Transaction savedTransaction = transactionRepository.save(transaction);

      TransactionDto returnTransactionDto = transactionMapper.toTransactionDto(savedTransaction);

      producer.sendMessageToTransactionQueue(returnTransactionDto);   //RabbitMQ message producer

      return returnTransactionDto;
    }

    public List<TransactionListDto>  getTransactions(Long id)
    {
        List<Transaction> transactionList = transactionRepository.findByAccount_Id(id);

        if(transactionList.isEmpty())
            throw new UserNotFoundException("Can't find transactions with account_id : " + id);


        List<TransactionListDto> transactionListDtos = transactionMapper.toDtoList(transactionList);

        for(int i=0; i<transactionListDtos.size(); i++)
        {
            transactionListDtos.get(i).setAccount_id(transactionList.get(i).getAccount().getId());
        }
        return transactionListDtos;
    }
}
