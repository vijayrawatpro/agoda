package com.agoda.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Hotel {
    private Long hotelId;
    private String city;
    private RoomType roomType;
    private BigDecimal price;

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return Objects.equals(hotelId, hotel.hotelId);
    }

    @Override public int hashCode() {
        return Objects.hash(hotelId);
    }

    public Hotel() {
    }

    public Hotel(Long hotelId, String city, RoomType roomType, BigDecimal price) {
        this.hotelId = hotelId;
        this.city = city;
        this.roomType = roomType;
        this.price = price;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public String getCity() {
        return city;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public static interface HotelIdStep {
        CityStep withHotelId(Long hotelId);
    }

    public static interface CityStep {
        RoomTypeStep withCity(String city);
    }

    public static interface RoomTypeStep {
        PriceStep withRoomType(RoomType roomType);
    }

    public static interface PriceStep {
        BuildStep withPrice(BigDecimal price);
    }

    public static interface BuildStep {
        Hotel build();
    }

    public static class Builder implements HotelIdStep, CityStep, RoomTypeStep, PriceStep, BuildStep {
        private Long hotelId;
        private String city;
        private RoomType roomType;
        private BigDecimal price;

        private Builder() {
        }

        public static HotelIdStep hotel() {
            return new Builder();
        }

        @Override
        public CityStep withHotelId(Long hotelId) {
            this.hotelId = hotelId;
            return this;
        }

        @Override
        public RoomTypeStep withCity(String city) {
            this.city = city;
            return this;
        }

        @Override
        public PriceStep withRoomType(RoomType roomType) {
            this.roomType = roomType;
            return this;
        }

        @Override
        public BuildStep withPrice(BigDecimal price) {
            this.price = price;
            return this;
        }

        @Override
        public Hotel build() {
            return new Hotel(
                this.hotelId,
                this.city,
                this.roomType,
                this.price
            );
        }
    }
}
