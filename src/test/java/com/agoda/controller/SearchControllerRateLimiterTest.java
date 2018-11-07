package com.agoda.controller;

import com.agoda.repository.SearchRepository;
import com.agoda.service.RateLimitingService;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerRateLimiterTest {

    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private SearchRepository searchRepository;
    @Autowired
    private RateLimitingService rateLimitingService;
    private MockMvc mvc;
    private ExecutorService executorService;
    private static final String SEARCH_URL = "/hotels/search";

    @Before
    public void init() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        executorService = Executors.newFixedThreadPool(500);
        rateLimitingService.cleanUp();
    }

    @After
    public void destroy() {
        executorService.shutdownNow();
    }

    /**
     * ApiKey : key1 Test will run 110 requests parallelly which will cause the api to throw RateLimitExceededException
     * for 100 requests
     *
     * @throws Exception
     */
    @Test
    public void shouldRateLimit() throws Exception {
        String apiKey = "key1";
        int requests = 10;
        int extraRequests = 100;
        List<Future<Integer>> futures = new ArrayList<>();
        for (int i = 0; i < requests + extraRequests; i++) {
            futures.add(executorService.submit(() -> {
                try {
                    MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(SEARCH_URL)
                        .param("query", "Bangkok")
                        .param("sortByPrice", "false")
                        .param("isAscending", "false")
                        .header("X-API-KEY", apiKey)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
                    int status = mvcResult.getResponse().getStatus();
                    return status;
                } catch (Exception e) {
                    e.printStackTrace();
                    return -1;
                }
            }));
        }
        int ok = 0;
        int tooMany = 0;
        int exception = 0;
        for (Future<Integer> future : futures) {
            switch (future.get()) {
                case 200:
                    ok++;
                    break;
                case 429:
                    tooMany++;
                    break;
                default:
                    exception++;
            }
        }
        Assert.assertEquals(10, ok);
        Assert.assertEquals(100, tooMany);
        Assert.assertEquals(0, exception);
    }
}
