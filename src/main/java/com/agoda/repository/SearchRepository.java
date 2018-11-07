package com.agoda.repository;

import com.agoda.converter.HotelConverter;
import com.agoda.domain.Hotel;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

@Repository
public class SearchRepository {

    @Autowired
    private HotelConverter hotelConverter;

    private Map<String, List<Hotel>> cityToHotel = new LinkedHashMap<>();

    @PostConstruct
    public void loadData() throws IOException {
        try (Stream<String> stream = Files.lines(
            Paths.get(new ClassPathResource("hoteldb.csv").getFile().getAbsolutePath()))) {
            stream.skip(1).forEach(line -> {
                Hotel hotel = hotelConverter.convert(line);
                List<Hotel> hotelsByCity = cityToHotel.getOrDefault(hotel.getCity(), new ArrayList<>());
                hotelsByCity.add(hotel);
                cityToHotel.put(hotel.getCity(), hotelsByCity);
            });
        }
    }

    public List<Hotel> search(String query, Boolean sortByPrice, Boolean isAscending) {
        List<Hotel> hotels = cityToHotel.get(query);
        if (sortByPrice) {
            if (isAscending) {
                Collections.sort(hotels, Comparator.comparing(Hotel::getPrice));
            } else {
                Collections.sort(hotels, Comparator.comparing(Hotel::getPrice).reversed());
            }
        }
        return hotels;
    }
}
