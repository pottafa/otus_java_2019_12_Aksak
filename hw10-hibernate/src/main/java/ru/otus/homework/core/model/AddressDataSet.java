package ru.otus.homework.core.model;

import javax.persistence.*;

@Entity
@Table(name = "address_table")
public class AddressDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "street")
    private String street;
}
