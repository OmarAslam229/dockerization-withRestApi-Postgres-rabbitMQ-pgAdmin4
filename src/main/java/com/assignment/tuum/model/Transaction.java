package com.assignment.tuum.model;

import com.assignment.tuum.model.enums.Currency;
import com.assignment.tuum.model.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
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
}
