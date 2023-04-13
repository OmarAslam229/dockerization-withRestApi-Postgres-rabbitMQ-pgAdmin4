package com.assignment.tuum.service;

import com.assignment.tuum.dtos.TransactionDto;
import com.assignment.tuum.exceptions.UserNotFoundException;
import com.assignment.tuum.mapper.TransactionMapper;
import com.assignment.tuum.model.Balance;
import com.assignment.tuum.model.Transaction;
import com.assignment.tuum.model.enums.Direction;
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
@Slf4j
@AllArgsConstructor
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private TransactionMapper transactionMapper;

    public TransactionDto createTransaction(TransactionDto transactionDto)
    {
        Transaction transaction = transactionMapper.toEntity(transactionDto);

        String error = "";

        if(transaction.getAmount() <= 0)
            error = "Invalid Amount";

        if(transaction.getDescription() == null)
            error = "Missing description";

      Balance balance=  balanceRepository.findByAccount_IdAndCurrency(transaction.getAccount().getId(),transaction.getCurrency());

      if(transaction.getDirection() == Direction.IN)
      {
          Long newBalance = balance.getBalance();
          newBalance += transaction.getAmount();
          balance.setBalance(newBalance);
          balanceRepository.save(balance);
      }
      else if(transaction.getAmount() <= balance.getBalance())
      {
          Long newBalance = balance.getBalance();
          newBalance -= transaction.getAmount();
          balance.setBalance(newBalance);
          balanceRepository.save(balance);
      }
        Transaction savedTransaction = transactionRepository.save(transaction);

      return transactionMapper.toDto(savedTransaction);
    }
}
