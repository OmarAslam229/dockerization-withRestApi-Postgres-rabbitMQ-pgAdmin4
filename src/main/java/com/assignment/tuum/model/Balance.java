package com.assignment.tuum.model;

import com.assignment.tuum.model.enums.Currency;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private Long balance;

    @ManyToOne
    @JsonIgnore
    private Account account;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "account_id", referencedColumnName = "id")
//    private Account account;


}
