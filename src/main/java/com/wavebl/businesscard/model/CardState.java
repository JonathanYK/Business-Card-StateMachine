package com.wavebl.businesscard.model;

import java.util.EnumSet;
import java.util.Set;

public enum CardState {
    KNOWN,
    ADDITIONAL_VERIFICATION,
    MANUAL_APPROVED,
    UNKNOWN,
    PENDING_VERIFICATION,
    STRONG_APPROVED;

    // Static set of verification states defined to check if a state is a verification state
    private static final Set<CardState> verificationStates = EnumSet.of(PENDING_VERIFICATION, ADDITIONAL_VERIFICATION);
    public static Set<CardState> getVerificationStates() {
        return verificationStates;
    }
    }
