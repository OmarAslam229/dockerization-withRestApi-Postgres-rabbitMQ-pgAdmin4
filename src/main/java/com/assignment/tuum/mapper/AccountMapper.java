package com.assignment.tuum.mapper;


import com.assignment.tuum.dtos.CreateAccountDto;
import com.assignment.tuum.dtos.ReturnAccountDto;
import com.assignment.tuum.dtos.TransactionDto;
import com.assignment.tuum.mapper.common.EntityMapper;
import com.assignment.tuum.model.Account;
import com.assignment.tuum.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper extends EntityMapper<Account,CreateAccountDto> {

    ReturnAccountDto toReturnAccountDto(Account account);
}
