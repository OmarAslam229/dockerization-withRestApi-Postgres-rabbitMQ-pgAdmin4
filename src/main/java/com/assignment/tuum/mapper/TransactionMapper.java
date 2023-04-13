package com.assignment.tuum.mapper;

import com.assignment.tuum.dtos.TransactionDto;
import com.assignment.tuum.mapper.common.EntityMapper;
import com.assignment.tuum.model.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionMapper extends EntityMapper<Transaction, TransactionDto> {
}
