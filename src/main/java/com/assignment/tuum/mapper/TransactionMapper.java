package com.assignment.tuum.mapper;

import com.assignment.tuum.dtos.TransactionCreateDto;
import com.assignment.tuum.dtos.TransactionDto;
import com.assignment.tuum.dtos.TransactionListDto;
import com.assignment.tuum.mapper.common.EntityMapper;
import com.assignment.tuum.model.Transaction;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper extends EntityMapper<Transaction, TransactionCreateDto> {

    @Override
    @Mapping(source = "account_id", target = "account.id")
    Transaction toEntity(TransactionCreateDto transactionDto);

    @Override
    @Mapping(source = "account.id", target = "account_id")
    TransactionCreateDto toDto(Transaction transaction);

    @Mapping(source = "account.id", target = "account_id")
    TransactionDto toTransactionDto(Transaction transaction);

  //  @IterableMapping(nullValueMappingStrategy = NullValueMappingStrategy.RETURN_NULL)
    @Mapping(source = "account.id", target = "account_id")
    List<TransactionListDto> toDtoList(List<Transaction> transaction);

   // List<TransactionListDto> toDtos(List<Transaction> entityList);
}
