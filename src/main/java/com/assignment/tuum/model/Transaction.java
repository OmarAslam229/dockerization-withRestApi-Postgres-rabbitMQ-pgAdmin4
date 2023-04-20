package com.assignment.tuum.model;

import com.assignment.tuum.model.enums.Currency;
import com.assignment.tuum.model.enums.Direction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @Column(name = "currency",length = 10,nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "amount", nullable = false)
    private long amount;

    @Column(name = "direction",length = 5,nullable = false)
    @Enumerated(EnumType.STRING)
    private Direction direction;

    @Column(name = "description")
    private String description;

    @Column(name = "balance_after_transaction")
    private Long balanceAfterTransaction;
}
