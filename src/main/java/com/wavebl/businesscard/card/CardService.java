package com.wavebl.businesscard.card;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private final CardRepository cardRepository;

    @Autowired
    private CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }
    public List<Card> getDemoCard() {
//        return List.of(
//                new Card(1L, "Dan", "Tel Aviv"
//                )
//        );
        return cardRepository.findAll();
    }
}
