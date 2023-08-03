package com.wavebl.businesscard;

import com.wavebl.businesscard.model.CardEvent;
import com.wavebl.businesscard.model.CardState;
import com.wavebl.businesscard.model.CardStateMachine;
import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class BusinesscardApplicationTests {

	@Autowired
	private CardStateMachine stateMachine;


	// UNIT TESTS
	@Test
	public void testInit() {
		assertNotNull(stateMachine);
	}

	@Test
	void testUnknownFlow() {
		CardState testState = stateMachine.transit(CardState.UNKNOWN, CardEvent.TRIGGER_VERIFICATION);
		testState = stateMachine.transit(testState, CardEvent.STRONG_VERIFICATION);
		assertEquals(testState, CardState.STRONG_APPROVED);
	}

	@Test
	void testKnownFlow() {
		CardState testState = stateMachine.transit(CardState.KNOWN, CardEvent.TRIGGER_VERIFICATION);
		testState = stateMachine.transit(testState, CardEvent.STRONG_VERIFICATION);
		assertEquals(testState, CardState.STRONG_APPROVED);
	}

	@Test
	void testManualVerificationFlow() {
		CardState testState = stateMachine.transit(CardState.KNOWN, CardEvent.MANUAL_VERIFICATION);
		assertEquals(testState, CardState.MANUAL_APPROVED);
	}

//	@Test
//	void testManualVerificationFalseFlow() {
//		CardState testState = stateMachine.transit(CardState.UNKNOWN, CardEvent.MANUAL_VERIFICATION);
//		assertNotEquals(testState, CardState.MANUAL_APPROVED);
//	}

}

