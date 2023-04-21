package com.assignment.tuum.model;

import com.assignment.tuum.model.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Table(name = "balance")
public class Balance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private Long id;

    @Column(name = "currency",length = 10,nullable = false)
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "available_balance", nullable = false)
    private Long availableBalance;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    @JsonIgnore
    private Account account;
}
