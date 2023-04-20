package com.assignment.tuum.repository;

import com.assignment.tuum.model.Balance;
import com.assignment.tuum.model.Transaction;
import com.assignment.tuum.model.enums.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance,Long> {

    Optional<Balance> findByAccount_IdAndCurrency (Long id, Currency c);
}
