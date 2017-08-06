package com.ilosci;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by Mike on 2017-08-01.
 */
@Entity
public class Test {
    @Id
    @GeneratedValue
    long id;

    long number;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public Test() {
    }

    public Test(long number) {
        this.number = number;
    }
}
