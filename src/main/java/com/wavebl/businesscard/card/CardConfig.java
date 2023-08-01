package com.wavebl.businesscard.card;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class CardConfig {
    @Bean
    public CommandLineRunner commandLineRunner(CardRepository cardRepo) {
        return args -> {
            Card dan = new Card("Dan", "Tel Aviv");
            Card dana = new Card("Dana", "Holon");

            cardRepo.saveAll(List.of(dan, dana));
        };
    }
}
