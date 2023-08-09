package com.wavebl.businesscard.model;

import com.wavebl.businesscard.exception.NextStateNotFoundException;

import java.util.*;

public class CardStateMachine {
    // transitions holds a map of states as key, and its values are sets of new states of CardEvent key
    private Map<CardState, Map<CardEvent, Set<CardState>>> transitions;

    public CardStateMachine() {
        this.transitions = new EnumMap<>(CardState.class);
    }

    public void addTransition(CardState fromState, CardEvent event, CardState toState) {
        if (!transitions.containsKey(fromState)) {
            transitions.put(fromState, new HashMap<>());
        }
        Map<CardEvent, Set<CardState>> eventMap = transitions.get(fromState);
        if (!eventMap.containsKey(event)) {
            eventMap.put(event, new HashSet<>());
        }
        Set<CardState> stateSet = eventMap.get(event);
        stateSet.add(toState);
    }

    public CardState transit(CardState currState, CardEvent occurredEvent) {

       // Get inner map of new states by currState
       Map<CardEvent, Set<CardState>> nextStateByEvent = transitions.getOrDefault(currState, new HashMap<>());

       // validate that next state is a valid state for occurredEvent, and it is deterministic (only one move available)
       if (nextStateByEvent.containsKey(occurredEvent) && nextStateByEvent.get(occurredEvent).size() == 1) {
            return nextStateByEvent.get(occurredEvent).iterator().next();
        }

        throw new NextStateNotFoundException ("Invalid event: " + occurredEvent + " for current state: " + currState);
    }
}

