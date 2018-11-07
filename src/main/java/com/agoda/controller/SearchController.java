package com.agoda.controller;

import com.agoda.domain.Hotel;
import com.agoda.service.SearchService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import static com.agoda.controller.BaseController.ok;

@RestController
@RequestMapping("/hotels")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping(value = "/search")
    @ResponseBody
    public ResponseEntity<List<Hotel>> search(@RequestParam String query,
        @RequestParam(defaultValue = "false") Boolean sortByPrice,
        @RequestParam(defaultValue = "true") Boolean isAscending) {
        return ok(searchService.search(query, sortByPrice, isAscending));
    }
}
