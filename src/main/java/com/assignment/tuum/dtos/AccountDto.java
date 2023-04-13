package com.assignment.tuum.dtos;

import com.assignment.tuum.model.Balance;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
public class AccountDto {

    private Long id;
    private String customerId;
    private char[] country;
    private List<Balance> balance;
}
