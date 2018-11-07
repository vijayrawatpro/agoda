package com.agoda.converter;

import com.agoda.domain.Hotel;
import com.agoda.domain.RoomType;
import java.math.BigDecimal;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class HotelConverter implements Converter<String, Hotel> {
    @Override public Hotel convert(String csvLine) {
        String[] row = csvLine.split(",");
        return Hotel.Builder.hotel()
            .withHotelId(Long.valueOf(row[1]))
            .withCity(row[0])
            .withRoomType(RoomType.fromDescription(row[2]))
            .withPrice(new BigDecimal(row[3]))
            .build();
    }
}
