package com.hilmirafiff.airbnb_clone_be.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppMessageEnum {

    USER("Pengguna", "User"),
    EXERCISE("Latihan","Exercise"),
    TEMPLATE("Contoh","Template"),
    MAP("Pemetaan", "Mapping"),
    USER_LEVEL("User level", "User level");

    private final String messageInd;
    private final String messageEn;
}

