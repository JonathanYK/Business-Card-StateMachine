package com.wavebl.businesscard.service;

import com.wavebl.businesscard.exception.CardMissingParamsException;
import com.wavebl.businesscard.exception.CardNotFoundException;
import com.wavebl.businesscard.exception.NextStateNotFoundException;
import com.wavebl.businesscard.model.Card;
import com.wavebl.businesscard.model.CardEvent;
import com.wavebl.businesscard.model.CardState;
import com.wavebl.businesscard.model.CardStateMachine;
import com.wavebl.businesscard.repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final CardStateMachine cardStateMachine;
    private static final String TRUSTED_SOURCE_KEY = System.getenv("TRUSTED_SOURCE_KEY");
    private static final String STRONG_VALIDATION_PHRASE = System.getenv("STRONG_VALIDATION_PHRASE");


    @Autowired
    public CardService(CardRepository cardRepository,CardStateMachine cardStateMachine) {
        this.cardRepository = cardRepository;
        this.cardStateMachine = cardStateMachine;
    }

    public List<Card> retrieveAllCards() {
        return cardRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Transactional
    public long classifyCard(Map<String, String> bodyParams) {

        Card card = validateBodyCardParams(bodyParams);

        // define source of card
        String trustedSourceKey = bodyParams.getOrDefault("trustedSourceKey", null);
        if (trustedSourceKey != null && trustedSourceKey.equals(TRUSTED_SOURCE_KEY)) {
            card.setState(CardState.KNOWN);
        } else { card.setState(CardState.UNKNOWN); }

        cardRepository.save(card);
        return card.getId();
    }

    private Card validateBodyCardParams(Map<String, String> bodyParams) {
        if(bodyParams.containsKey("name") && bodyParams.containsKey("address")) {
            return new Card(bodyParams.get("name"), bodyParams.get("address"));
        }
        else {
            throw new CardMissingParamsException("card init missing mandatory params: 'name', 'address'");
        }
    }

    @Transactional
    public CardState transitUploadedCard(Long cardId) {
        Card currCard = cardRepository.findById(cardId)
                .orElseThrow(()-> new CardNotFoundException(
                        "card with id: " + cardId + " does not exist!"
                ));

        // move currCard state to verification state by trigger verification event
        CardState nextState = cardStateMachine.transit(currCard.getState(), CardEvent.TRIGGER_VERIFICATION);
        if (nextState == null) {
            throw new NextStateNotFoundException(
                    "transition from state: " + currCard.getState() + " to state: " + nextState + " not found!"
            );
        }
        // if currCard reached verification state, execute verification:
        if (CardState.getVerificationStates().contains(nextState)) {
            CardEvent currEvent;
            if(strongValidateAddr(currCard.getAddress())) {
                currEvent = CardEvent.STRONG_VERIFICATION;
            } else currEvent = CardEvent.ABORT_VERIFICATION;

            // move currCard state to approved or reject state, according to verification event
            nextState = cardStateMachine.transit(nextState, currEvent);

            if (nextState == null) {
                throw new NextStateNotFoundException (
                        "transition from state: " + currCard.getState() + " not found!"
                );
            }
        }
        currCard.setState(nextState);
        return nextState;
    }

    // strongValidateAddr will execute strong verification,
    // verifies that address contains STRONG_VALIDATION_PHRASE
    private Boolean strongValidateAddr(String address) {
        return address.toLowerCase().contains(STRONG_VALIDATION_PHRASE);
    }

    @Transactional
    public void transitCardService(Long cardId, CardEvent cardEvent) {

        Card currCard = cardRepository.findById(cardId).orElse(null);
        if (currCard == null) {
            throw new CardNotFoundException("Card with id: " + cardId + " does not exist!");
        }
        currCard.setState(cardStateMachine.transit(currCard.getState(), cardEvent));
    }

    @Transactional
    public List<Card> getAllCardsInSpecificState(String state) {
        CardState cardState;
        try {
            cardState = CardState.valueOf(state);
        } catch (IllegalArgumentException ex) {
            log.error(ex.getMessage());
            throw new RuntimeException("Failed locate state: " + state);
        }
        return cardRepository.findByState(cardState);
    }

    public void addSourceKey(Map<String, String> cardMap) {
        cardMap.put("trustedSourceKey", TRUSTED_SOURCE_KEY);
    }
}
