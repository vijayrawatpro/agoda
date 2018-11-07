package com.agoda.service;

import com.agoda.domain.Hotel;
import com.agoda.repository.SearchRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    private SearchRepository searchRepository;

    public List<Hotel> search(String query, Boolean sortByPrice, Boolean isAscending) {
        List<Hotel> hotels = searchRepository.search(query, sortByPrice, isAscending);
        return hotels;
    }
}
