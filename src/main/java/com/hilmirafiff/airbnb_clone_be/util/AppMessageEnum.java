package com.hilmirafiff.airbnb_clone_be.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppMessageEnum {

    USER("Pengguna", "User"),
    USER_LEVEL("User level", "User level"),
    PROPERTY("Properti", "Property"),
    BOOKING("Pemesanan", "Booking"),
    IMAGE("Gambar","Images"),
    REVIEW("Tinjauan","Review");

    private final String messageInd;
    private final String messageEn;
}

