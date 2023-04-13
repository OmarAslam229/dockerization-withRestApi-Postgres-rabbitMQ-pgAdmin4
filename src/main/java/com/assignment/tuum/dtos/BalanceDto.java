package com.assignment.tuum.dtos;

import com.assignment.tuum.model.Account;
import com.assignment.tuum.model.enums.Currency;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BalanceDto {

    private Currency currency;
    private Long balance;
    private Account account;
}
