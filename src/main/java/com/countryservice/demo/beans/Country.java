package com.countryservice.demo.beans;

import javax.persistence.*;

@Entity
@Table(name = "Country")
public class Country {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            @Column(name = "id")
    int id;
    @Column(name = "country_name")
    String countryName;
    @Column(name = "capital")
    String countryCapital;

    public Country(int id, String countryName, String countryCapital) {
        this.id = id;
        this.countryName = countryName;
        this.countryCapital = countryCapital;
    }

    public Country() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCapital() {
        return countryCapital;
    }

    public void setCountryCapital(String countryCapital) {
        this.countryCapital = countryCapital;
    }

    @Override
    public String toString() {
        return "Country{" +
                "id=" + id +
                ", countryName='" + countryName + '\'' +
                ", countryCapital='" + countryCapital + '\'' +
                '}';
    }
}
