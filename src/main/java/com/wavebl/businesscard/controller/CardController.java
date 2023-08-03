package com.wavebl.businesscard.controller;

import com.wavebl.businesscard.model.Card;
import com.wavebl.businesscard.model.CardEvent;
import com.wavebl.businesscard.model.CardState;
import com.wavebl.businesscard.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path="/card")
public class CardController {

    private final CardService cardService;

    @Autowired
    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Card>> getAllCards() {
        List<Card> cards = cardService.retrieveAllCards();
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCard(@RequestBody Map<String, String> bodyParams) {
        Long cardId = cardService.classifyCard(bodyParams);
        return ResponseEntity.ok("card added with id: " + cardId);
    }

    @PutMapping("/verify")
    public ResponseEntity<String> verifyCard(@RequestParam(name = "cardId") Long cardId) {
        CardState appliedState = cardService.transitUploadedCard(cardId);
        return ResponseEntity.ok("final state for cardId: " + cardId + " is: " + appliedState);
    }

    @PutMapping("/manual")
    public ResponseEntity<String> manApproveCard(@RequestParam(name = "cardId") Long cardId) {
        cardService.transitCardService(cardId, CardEvent.MANUAL_VERIFICATION);
        return ResponseEntity.ok("manual done for cardId: " + cardId);
    }

    @PutMapping("/unverify")
    public ResponseEntity<String> unverifyCard(@RequestParam(name = "cardId") Long cardId) {
        cardService.transitCardService(cardId, CardEvent.UNVERIFY);
        return ResponseEntity.ok("unverify done for cardId: " + cardId);
    }

    @GetMapping("/specificState/{state}")
    public List<Card> getCardsByState(@PathVariable String state) {
        return cardService.getAllCardsInSpecificState(state);
    }
}
