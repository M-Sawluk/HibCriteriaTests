package com.ilosci;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Mike on 2017-07-15.
 */
@Entity
public class IlosciUmowne {

    @Id
    @GeneratedValue
    private long id;

    private String name;

    @ManyToMany
    private Set<Jednostka> jednostka;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Jednostka> getJednostka() {
        return jednostka;
    }

    public void setJednostka(Set<Jednostka> jednostka) {
        this.jednostka = jednostka;
    }
}
