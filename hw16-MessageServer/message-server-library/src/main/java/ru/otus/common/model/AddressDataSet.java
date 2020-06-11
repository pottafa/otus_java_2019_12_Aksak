package ru.otus.common.model;

import javax.persistence.*;

@Entity
@Table(name = "address_table")
public class AddressDataSet {
    @Override
    public String toString() {
        return "Address: " +
                "street='" + street + '\'';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "street")
    private String street;

    public AddressDataSet() {

    }

    public AddressDataSet(String street) {
        this.street = street;
    }
}
