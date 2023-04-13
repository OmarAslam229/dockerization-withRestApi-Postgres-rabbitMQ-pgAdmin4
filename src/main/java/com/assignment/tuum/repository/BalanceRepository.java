package com.assignment.tuum.repository;

import com.assignment.tuum.model.Balance;
import com.assignment.tuum.model.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceRepository extends JpaRepository<Balance,Long> {

    //Contract findByEmployee_IdAndActiveTrue(@Param("id") Long id);
    Balance findByAccount_IdAndCurrency(Long id, Currency c);
}
