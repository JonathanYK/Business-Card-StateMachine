package com.wavebl.businesscard.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Table(name = "cards")
@Getter
@Setter
@NoArgsConstructor
@ToString
@Entity
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
    private String address;
    @Enumerated(EnumType.STRING)
    private CardState state;

    public Card(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public Map<String, String> cardAsMap() {
        Map<String, String> retMap = new HashMap<>();
        retMap.put("name", this.name);
        retMap.put("address", this.address);
        return retMap;
    }
}
