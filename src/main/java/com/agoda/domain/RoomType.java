package com.agoda.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public enum RoomType {
    DELUXE("Deluxe"), SUPERIOR("Superior"), SWEET_SUITE("Sweet Suite");

    private static Map<String, RoomType> descriptionToRoomType = new LinkedHashMap<>();

    static {
        descriptionToRoomType = new LinkedHashMap<>();
        descriptionToRoomType.put("Deluxe", DELUXE);
        descriptionToRoomType.put("Superior", SUPERIOR);
        descriptionToRoomType.put("Sweet Suite", SWEET_SUITE);
    }

    String description;

    RoomType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static RoomType fromDescription(String description) {
        return descriptionToRoomType.get(description);
    }

    @Override public String toString() {
        return this.description;
    }
}
