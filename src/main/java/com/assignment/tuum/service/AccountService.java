package com.assignment.tuum.service;

import com.assignment.tuum.dtos.AccountDto;
import com.assignment.tuum.exceptions.UserNotFoundException;
import com.assignment.tuum.mapper.AccountMapper;
import com.assignment.tuum.model.Account;
import com.assignment.tuum.model.Balance;
import com.assignment.tuum.repository.AccountRepository;
import com.assignment.tuum.repository.BalanceRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
@AllArgsConstructor
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BalanceRepository balanceRepository;

    @Autowired
    private AccountMapper accountMapper;

    public AccountDto createAccount(AccountDto accountDto) throws HttpMessageNotReadableException
    {
        Account account = accountMapper.toEntity(accountDto);
        Account savedAccount = accountRepository.save(account);

        List<Balance> balanceList = account.getBalance();
        balanceRepository.saveAll(balanceList);

        return accountMapper.toDto(savedAccount);
    }

    public AccountDto getAccountDetails(Long id)
    {
        Account account = accountRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Account With Id #" + id + " not found "));
        return accountMapper.toDto(account);
    }
}

