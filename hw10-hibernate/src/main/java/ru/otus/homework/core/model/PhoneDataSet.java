package ru.otus.homework.core.model;

import javax.persistence.*;

@Entity
@Table(name = "phones_table")
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "number_of_phones")
    private String number;
}
