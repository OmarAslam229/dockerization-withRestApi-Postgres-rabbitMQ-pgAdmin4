package com.assignment.tuum.dtos;

import com.assignment.tuum.model.Balance;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ReturnAccountDto {

    private Long id;
    private String customerId;
    private List<Balance> balances ;
}
