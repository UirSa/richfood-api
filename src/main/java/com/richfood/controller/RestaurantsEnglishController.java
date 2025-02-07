package com.richfood.controller;


import com.richfood.model.Restaurants;
import com.richfood.model.RestaurantsEnglish;
import com.richfood.service.RestaurantsEnglishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/restaurantsEnglish")
public class RestaurantsEnglishController {
    @Autowired
    private RestaurantsEnglishService restaurantsEnglishService;

    //Restaurants list by country and category for checkbox and selected
    @GetMapping(value = {"/list","/{country}/list/{category}", "/{country}/list","/list/{category}",})
    public Page<RestaurantsEnglish> searchRestaurantsEnglishByCountryAndCategory(@PathVariable(required = false) String country,
                                                                          @PathVariable(required = false) String category,
                                                                          @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        return restaurantsEnglishService.searchRestaurantsEnglishByCountryAndCategory(country, category ,pageable);
    }
    //Restaurants by id
    @GetMapping("/{restaurantId}")
    public Optional<RestaurantsEnglish> getRestaurantsEnglishById(@PathVariable Integer restaurantId){
        return restaurantsEnglishService.getRestaurantsEnglishById(restaurantId);
    }

    //Restaurants by lat and long for recommend
    @GetMapping("")
    public Optional<RestaurantsEnglish> getRestaurantByLatAndLong(@RequestParam(name = "lat",required = true) Double latitude,
                                                       @RequestParam(name = "long",required = true) Double longitude){

        return restaurantsEnglishService.getRestaurantByLatAndLong(latitude,longitude);
    }
}
