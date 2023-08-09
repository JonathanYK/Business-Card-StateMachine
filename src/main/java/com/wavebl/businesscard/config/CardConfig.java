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
    public CommandLineRunner initCardData(CardRepository cardRepo, CardService service, CardController controller) {
        return args -> {
            List<Card> demoCards = createDemoCards();
            addDemoCards(demoCards, service, controller);

            List<Card> finalDemoCards = new ArrayList<>(cardRepo.findAll());

            controller.manApproveCard(finalDemoCards.get(2).getId());
            controller.verifyCard(finalDemoCards.get(1).getId());

            finalDemoCards = new ArrayList<>(cardRepo.findAll());
            log.info("Added demo cards to DB: {}", finalDemoCards);
        };
    }

    private List<Card> createDemoCards() {
        List<Card> demoCards = new ArrayList<>();
        demoCards.add(new Card("Dan", "Kfar Aviv"));
        demoCards.add(new Card("Tomer", "Kfar Saba"));
        demoCards.add(new Card("Rotem", "Ashdod"));
        return demoCards;
    }

    private void addDemoCards(List<Card> demoCards, CardService service, CardController controller) {
        for (Card card : demoCards) {
            Map<String, String> cardMap = card.cardAsMap();
            service.addSourceKey(cardMap);
            controller.addCard(cardMap);
        }
    }
}
