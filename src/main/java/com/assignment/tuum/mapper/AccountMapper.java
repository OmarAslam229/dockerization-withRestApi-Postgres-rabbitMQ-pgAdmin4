package com.assignment.tuum.mapper;

import com.assignment.tuum.dtos.AccountDto;
import com.assignment.tuum.mapper.common.EntityMapper;
import com.assignment.tuum.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper extends EntityMapper<Account,AccountDto> {
}
