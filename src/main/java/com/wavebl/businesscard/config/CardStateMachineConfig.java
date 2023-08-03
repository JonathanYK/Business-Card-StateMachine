package com.wavebl.businesscard.config;

import com.wavebl.businesscard.model.CardEvent;
import com.wavebl.businesscard.model.CardState;
import com.wavebl.businesscard.model.CardStateMachine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardStateMachineConfig {
	@Bean
	public CardStateMachine cardStateMachineInit() {
		CardStateMachine cardStateMachine = new CardStateMachine();
		fillStateMachine(cardStateMachine);

		return cardStateMachine;
	}

	public void fillStateMachine(CardStateMachine cardStateMachine) {

		// KNOWN states transitions:
		cardStateMachine.addTransition(CardState.KNOWN, CardEvent.MANUAL_VERIFICATION, CardState.MANUAL_APPROVED);
		cardStateMachine.addTransition(CardState.MANUAL_APPROVED, CardEvent.UNVERIFY, CardState.KNOWN);
		cardStateMachine.addTransition(CardState.KNOWN, CardEvent.TRIGGER_VERIFICATION, CardState.ADDITIONAL_VERIFICATION);
		cardStateMachine.addTransition(CardState.ADDITIONAL_VERIFICATION, CardEvent.ABORT_VERIFICATION, CardState.KNOWN);
		cardStateMachine.addTransition(CardState.ADDITIONAL_VERIFICATION, CardEvent.STRONG_VERIFICATION, CardState.STRONG_APPROVED);

		// UNKNOWN states transitions:
		cardStateMachine.addTransition(CardState.UNKNOWN, CardEvent.TRIGGER_VERIFICATION, CardState.PENDING_VERIFICATION);
		cardStateMachine.addTransition(CardState.PENDING_VERIFICATION, CardEvent.ABORT_VERIFICATION, CardState.UNKNOWN);
		cardStateMachine.addTransition(CardState.PENDING_VERIFICATION, CardEvent.STRONG_VERIFICATION, CardState.STRONG_APPROVED);
	}

}
