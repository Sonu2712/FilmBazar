package com.film.debugview;

public enum ApiEndpoints {
    UAT("UAT"), MOCK_MODE("Mock Mode");

    public final String name;

    ApiEndpoints(String name) {
        this.name = name;
    }

    public static ApiEndpoints from(String endpoint) {
        for (ApiEndpoints value : values()) {
            if (value.name != null && value.name.equals(endpoint)) {
                return value;
            }
        }
        return UAT;
    }

    public static boolean isMockMode(String endpoint) {
        return from(endpoint) == MOCK_MODE;
    }

    @Override public String toString() {
        return name;
    }
}