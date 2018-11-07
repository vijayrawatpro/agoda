package com.agoda.controller;

import com.agoda.domain.Hotel;
import com.agoda.repository.SearchRepository;
import com.agoda.service.RateLimitingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mvc;

    private static final String SEARCH_URL = "/hotels/search";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @Autowired
    private SearchRepository searchRepository;

    @Autowired
    private RateLimitingService rateLimitingService;

    @Before
    public void init() {
        this.mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        rateLimitingService.cleanUp();
    }

    @After
    public void destroy() {
    }

    @Test
    public void shouldSearchByCity() throws Exception {
        List<Hotel> hotels = searchRepository.search("Bangkok", false, false);
        mvc.perform(MockMvcRequestBuilders.get(SEARCH_URL)
            .param("query", "Bangkok")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(OBJECT_MAPPER.writeValueAsString(hotels))));
    }

    @Test
    public void shouldSearchByCityAndSortAsc() throws Exception {
        List<Hotel> hotels = searchRepository.search("Bangkok", true, true);
        mvc.perform(MockMvcRequestBuilders.get(SEARCH_URL)
            .param("query", "Bangkok")
            .param("sortByPrice", "true")
            .param("isAscending", "true")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(OBJECT_MAPPER.writeValueAsString(hotels))));
    }

    @Test
    public void shouldSearchByCityAndSortDesc() throws Exception {
        List<Hotel> hotels = searchRepository.search("Bangkok", true, false);
        mvc.perform(MockMvcRequestBuilders.get(SEARCH_URL)
            .param("query", "Bangkok")
            .param("sortByPrice", "true")
            .param("isAscending", "false")
            .contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(OBJECT_MAPPER.writeValueAsString(hotels))));
    }
}