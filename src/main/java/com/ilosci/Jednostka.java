package com.ilosci;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by Mike on 2017-07-15.
 */

@Entity
public class Jednostka {

    @Id
    @GeneratedValue
    private long id;

    private String name;

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

    @ManyToMany(mappedBy = "jednostka")
    private Set<IlosciUmowne> ilosciUmowne;

    public Set<IlosciUmowne> getIlosciUmowne() {
        return ilosciUmowne;
    }

    public void setIlosciUmowne(Set<IlosciUmowne> ilosciUmowne) {
        this.ilosciUmowne = ilosciUmowne;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private Kategoria kategoria;

    public Kategoria getKategoria() {
        return kategoria;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Jednostka)) return false;

        Jednostka jednostka = (Jednostka) o;

        return new org.apache.commons.lang3.builder.EqualsBuilder()
                .append(getId(), jednostka.getId())
                .append(getName(), jednostka.getName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new org.apache.commons.lang3.builder.HashCodeBuilder(17, 37)
                .append(getId())
                .append(getName())
                .toHashCode();
    }

    @Override
    public String toString() {
        return "Jednostka{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
