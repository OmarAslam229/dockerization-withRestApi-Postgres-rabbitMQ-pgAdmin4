package com.assignment.tuum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.dynalink.linker.LinkerServices;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.print.DocFlavor;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customer_id",length = 30, unique = true, nullable = false)
    private String customerId;

    @Column(name = "Country", length = 60 ,nullable = false)
    @JsonIgnore
    private char[] country;

    private String check;

//    @OneToMany(mappedBy = "account") //means this will be mapped by an object 'account' available in Balance class
//    private List<Balance> balance;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    private List<Balance> balance;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id",referencedColumnName = "id")
    @JsonIgnore
    private List<Transaction> transactions;

}
