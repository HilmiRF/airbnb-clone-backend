package com.hilmirafiff.airbnb_clone_be.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AppErrorEnum {

    OK("ABN-200", "Berhasil", "Successful"),
    CREATED("ABN-200", "Dibuat", "Created"),
    UPDATED("ABN-200", "Diperbarui", "Updated"),
    CLOSED("ABN-200", "Ditutup", "Closed"),
    FETCHED("ABN-200", "Diambil", "Fetched"),
    INACTIVE("ABN-200", "Dinonaktifkan", "Set to inactive"),
    ACTIVE("ABN-200", "Diaktifkan", "Set to active"),

    LOGOFF_SUCCESS("ABN-200", "Logoff Sukses", "Logoff Success"),
    USER_CURRENTLY_LOG_ON("ABN-200", "Pengguna ini sedang logged on", "This user is currently logged on"),

    // Global
    INTERNAL_SERVER_ERROR("ABN-500", "Maaf, sepertinya ada kesalahan pada server kami", "Sorry, there seems to be an error in our server"),
    MANDATORY_FIELD_MISSING("ABN-400", "Field mandatory belum terisi : {0}", "Mandatory field missing : {0}"),
    RESOURCE_NOT_FOUND("DITA-404", "{0} tidak ditemukan", "{0} not found"),

    // Unauthorized
    WRONG_USERNAME_OR_PASSWORD("ABN-401", "Kredensial ini tidak cocok dengan catatan kami", "Sorry, these credentials do not match our records"),
    USER_NOT_FOUND("ABN-401", "User tidak dapat ditemukan", "User not found"),
    INVALID_JWT("ABN-401", "Token JWT tidak valid", "Invalid JWT token"),
    EXPIRED_JWT("ABN-401", "Token JWT kedaluwarsa", "Expired JWT token"),
    SECURITY_JWT("ABN-401", "Token JWT tidak didukung", "Unsupported JWT token"),
    EMPTY_JWT_CLAIMS("ABN-401", "Claims JWT kosong", "JWT claims string is empty"),
    USER_ALREADY_EXIST("ABN-401", "User sudah ada", "User already exist");

    private final String appErrorCode;
    private final String appErrorMessageInd;
    private final String appErrorMessageEn;
}
