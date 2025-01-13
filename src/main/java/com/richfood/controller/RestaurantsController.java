package com.richfood.controller;

import com.richfood.dto.RestaurantsDto;
import com.richfood.model.BusinessHours;
import com.richfood.model.Restaurants;
import com.richfood.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("")
public class RestaurantsController {
    @Autowired
    private RestaurantsService restaurantsService;


    @GetMapping("/restaurants")
    public Page<Restaurants> searchRestaurants(@RequestParam int page
            , @RequestParam int size, @RequestParam String country){
        Pageable pageable = PageRequest.of(page,size);
        Page<Restaurants> restaurantsPage= restaurantsService.searchRestaurants(country,pageable);
        return restaurantsPage;
    }


    @PostMapping("/restaurants")
    public void test2(@RequestBody RestaurantsDto restaurantsDto){
        restaurantsService.saveRestaurants(restaurantsDto);
    }
}
