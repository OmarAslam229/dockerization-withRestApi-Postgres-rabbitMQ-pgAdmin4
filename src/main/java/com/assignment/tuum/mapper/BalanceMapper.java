package com.assignment.tuum.mapper;

import com.assignment.tuum.dtos.BalanceDto;
import com.assignment.tuum.mapper.common.EntityMapper;
import com.assignment.tuum.model.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BalanceMapper extends EntityMapper<Balance,BalanceDto>{
}
