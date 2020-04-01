package ru.otus.homework.core.model;

import javax.persistence.*;

@Entity
@Table(name = "phones_table")
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    @Column(name = "number_of_phones")
    private String number;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    User user;

    public PhoneDataSet() {
    }

    public PhoneDataSet(String number) {
        this.number = number;
    }
}
