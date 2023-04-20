package com.assignment.tuum.dtos;

import com.assignment.tuum.model.enums.Currency;
import com.assignment.tuum.model.enums.Direction;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder=true)
public class TransactionCreateDto {

    private Long account_id;
    private Currency currency;
    private long amount;
    private Direction direction;
    private String description;
}
