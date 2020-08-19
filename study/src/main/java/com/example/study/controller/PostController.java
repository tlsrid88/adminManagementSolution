package com.example.study.controller;

import com.example.study.model.SearchPram;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PostController {

    @PostMapping(value = "postMethod")
    public SearchPram postMethod(@RequestBody SearchPram searchPram) {

        System.out.println(searchPram);

        return  searchPram;
    }

    @PutMapping
    public void put() {

    }

    @PatchMapping
    public void patch() {

    }
}
