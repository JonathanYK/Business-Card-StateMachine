package com.wavebl.businesscard.config;

import com.wavebl.businesscard.model.Card;
import com.wavebl.businesscard.controller.CardController;
import com.wavebl.businesscard.service.CardService;
import com.wavebl.businesscard.repository.CardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Configuration
@Slf4j
public class CardConfig {
    @Bean
    public CommandLineRunner commandLineRunner(CardRepository cardRepo, CardService service, CardController controller) {
        return args -> {
            Card dan = new Card("Dan", "Tel Aviv");
            Card tomer = new Card("Tomer", "Kfar Saba");
            Card rotem = new Card("Rotem", "Ashdod");


            // add initCards as UNKNOWN
            createCards(new ArrayList<>(List.of(dan, tomer)), service, controller, false);

            // add initCards as KNOWN
            createCards(new ArrayList<>(List.of(rotem)), service, controller, true);

            ArrayList<Card> finalDemoCards = new ArrayList<>(cardRepo.findAll());

            controller.manApproveCard(finalDemoCards.get(2).getId());
            controller.verifyCard(finalDemoCards.get(1).getId());

            finalDemoCards = new ArrayList<>(cardRepo.findAll());
            log.info("Added demo cards to DB: " + finalDemoCards);

        };
    }

    public void createCards(List<Card> cardsList, CardService service, CardController controller, boolean isKnown) {
        for (Card card : cardsList) {
            Map<String, String> cardMap = card.cardAsMap();
            if (isKnown) {
                service.addSourceKey(cardMap);
            }
            controller.addCard(cardMap);
        }
    }
}
