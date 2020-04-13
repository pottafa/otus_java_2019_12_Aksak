package ru.otus.homework.dataBase.core.model;

import javax.persistence.*;

@Entity
@Table(name = "phones_table")
public class PhoneDataSet {
    @Override
    public String toString() {
        return "Phone:" +
                "number='" + number + '\'' ;
    }

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
