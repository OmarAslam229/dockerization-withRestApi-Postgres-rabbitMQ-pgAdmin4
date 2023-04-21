package com.assignment.tuum.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.dynalink.linker.LinkerServices;
import lombok.*;

import javax.persistence.*;
import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "customer_id",length = 30, unique = true, nullable = false)
    private String customerId;

    @Column(name = "country", length = 60 ,nullable = false)
    private String country;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL) //means this will be mapped by an object 'account' available in Balance class
    private List<Balance> balances ;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

}
