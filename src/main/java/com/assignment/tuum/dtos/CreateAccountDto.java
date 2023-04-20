package com.assignment.tuum.dtos;

import com.assignment.tuum.model.Balance;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
public class CreateAccountDto {

    private String customerId;
    private String country;
    private List<Balance> balances ;
}
