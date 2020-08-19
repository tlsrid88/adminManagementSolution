package com.example.study.controller;

import com.example.study.model.SearchPram;
import com.example.study.model.network.Header;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api") // localhost:8080/api
public class GetController {

    @RequestMapping(method = RequestMethod.GET, path = "/getMethod") // localhost:8080/api/getMethod
    public String getRequest() {

        return "Hi getMethod";
    }

    @GetMapping("getParameter") // localhost:8080/api/getParameter
    public String getParameter(@RequestParam String id, @RequestParam(name = "password") String pwd) {
        String password = "bbbbb";

        System.out.println("id : " + id);
        System.out.println("password : " + pwd);
        return id + pwd;
    }

    // localhost:8080/api/multiParameter?account=abcd&
    @GetMapping("getMultiParameter")
    public SearchPram getMultiParameter(SearchPram searchPram) {

        System.out.println(searchPram.getAccount());
        System.out.println(searchPram.getPage());
        System.out.println(searchPram.getEmail());

        return searchPram;
    }

    @GetMapping("/header")
    public Header getHeader() {

        // {"resultCode" : "OK" , "description" : "OK" }
        return Header.builder().description("OK").resultCode("OK").build();
    }

}
