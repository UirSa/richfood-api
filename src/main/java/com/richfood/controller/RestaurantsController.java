package com.richfood.controller;

import com.richfood.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/restaurants")
public class RestaurantsController {
    @Autowired
    private RestaurantsService restaurantsService;

    @GetMapping("")
    public void test1(){

    }

}
