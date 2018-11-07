package com.agoda.repository;

import com.agoda.domain.Hotel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchRepositoryTest {

    @Autowired
    private SearchRepository searchRepository;

    @Test
    public void shouldSearchByCity() {
        String city = "Bangkok";
        List<Hotel> result = searchRepository.search(city, false, false);
        assertNotNull(result);
        assertEquals(7, result.size());
    }

    @Test
    public void shouldSearchByCityAndSortAsc() {
        String city = "Bangkok";
        List<Hotel> result = searchRepository.search(city, true, true);
        assertNotNull(result);
        assertEquals(7, result.size());
        List<Hotel> tmp = new ArrayList<>(result);
        Collections.sort(tmp, Comparator.comparing(Hotel::getPrice));
        assertEquals(tmp, result);
    }

    @Test
    public void shouldSearchByCityAndSortDesc() {
        String city = "Bangkok";
        List<Hotel> result = searchRepository.search(city, true, false);
        assertNotNull(result);
        assertEquals(7, result.size());
        List<Hotel> tmp = new ArrayList<>(result);
        Collections.sort(tmp, Comparator.comparing(Hotel::getPrice).reversed());
        assertEquals(tmp, result);
    }
}
