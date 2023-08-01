package com.wavebl.businesscard.card;

import jakarta.persistence.*;

@Entity
@Table
public class Card {
    @Id
    @SequenceGenerator(
            name = "card_id_sequence",
            sequenceName = "card_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "card_id_sequence"
    )
    private Long id;
    private String name;
    private String Address;

    public Card() { }

    public Card(String name, String address) {
        this.name = name;
        Address = address;
    }

    public Card(Long id, String name, String address) {
        this.id = id;
        this.name = name;
        Address = address;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return Address;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Address='" + Address + '\'' +
                '}';
    }

}
