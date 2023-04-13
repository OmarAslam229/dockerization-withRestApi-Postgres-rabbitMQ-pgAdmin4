package com.assignment.tuum.dtos;

import com.assignment.tuum.model.Account;
import com.assignment.tuum.model.enums.Currency;
import com.assignment.tuum.model.enums.Direction;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
public class TransactionDto {

    private Long id;
    private Account account;
    private Currency currency;
    private long amount;
    private Direction direction;
    private String description;
}
