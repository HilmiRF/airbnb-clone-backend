package com.hilmirafiff.airbnb_clone_be.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppConstant {
    public static final String SECURITY_SCHEME_NAME = "Bearer Authentication";
    public static final String X_CLIENT_ID = "X-Client-Id";
    public static final String APP_NAME = "AIRBNB";

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Method {
        public static final String POST = "POST";
        public static final String PATCH = "PATCH";
        public static final String GET = "GET";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class Status {
        public static final String SUCCESS = "Success";
        public static final String ERROR = "Error";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FlagStatus {
        public static final String ACTIVE = "Active";
        public static final String INACTIVE = "Inactive";
    }
}

