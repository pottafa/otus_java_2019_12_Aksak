package ru.otus.homework.core.model;

import ru.otus.homework.jdbc.mapper.parser.Id;

public class Account {
    @Id
    long no;
    String type;
    int rest;

    public Account(long no, String type, int rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    public long getNo() {
        return no;
    }

    public int getRest() {
        return rest;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
