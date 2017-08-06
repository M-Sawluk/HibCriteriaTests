package com.ilosci;

/**
 * Created by Mike on 2017-08-06.
 */
public class GroupDTO {

    String name;
    String jednostkaName;
    String KategoriaName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJednostkaName() {
        return jednostkaName;
    }

    public void setJednostkaName(String jednostkaName) {
        this.jednostkaName = jednostkaName;
    }

    public String getKategoriaName() {
        return KategoriaName;
    }

    public void setKategoriaName(String kategoriaName) {
        KategoriaName = kategoriaName;
    }

    public void setJedn(Jednostka jednostka){
        this.jednostkaName = jednostka.getName();
    }

    public void setKategoria(Kategoria kategoria){
        this.KategoriaName = kategoria.getName();
    }


    public GroupDTO() {
    }

    @Override
    public String toString() {
        return "GroupDTO{" +
                "name='" + name + '\'' +
                ", jednostkaName='" + jednostkaName + '\'' +
                ", KategoriaName='" + KategoriaName + '\'' +
                '}';
    }

    public GroupDTO(String name, Kategoria kategoria) {
        this.name = name;
        this.KategoriaName = kategoria.getName();
    }

    public GroupDTO(String name, Jednostka jednostka, Kategoria kategoria) {
        this.name = name;
        this.jednostkaName = jednostka.getName();
        this.KategoriaName = kategoria.getName();
    }

    public GroupDTO(String name, Jednostka jednostka) {
        this.name = name;
        this.jednostkaName = jednostka.getName();
    }

    public GroupDTO(String name, String kategoria) {
        this.name = name;
        this.KategoriaName = kategoria;
    }
}
