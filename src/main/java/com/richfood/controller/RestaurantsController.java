package com.richfood.controller;

import com.richfood.dto.RestaurantsDto;
import com.richfood.model.BusinessHours;
import com.richfood.model.Restaurants;
import com.richfood.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<BusinessHours> test1(){
        List<Restaurants> list =restaurantsService.searchRestaurants();
        List<BusinessHours> allBusinessHoursList= new ArrayList<>();
        for (Restaurants restaurants : list) {
            List<BusinessHours> businessHoursList=restaurants.getBusinessHours();
            allBusinessHoursList.addAll(businessHoursList);
        }
        return allBusinessHoursList;

    }

    @PostMapping("/restaurants")
    public void test2(@RequestBody RestaurantsDto restaurantsDto){
        restaurantsService.saveRestaurants(restaurantsDto);
    }
}
