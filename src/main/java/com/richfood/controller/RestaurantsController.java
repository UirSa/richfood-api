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
@RequestMapping("/restaurants")
public class RestaurantsController {
    @Autowired
    private RestaurantsService restaurantsService;


    @GetMapping(value = {"/{country}/list/{category}", "/{country}/list","/list/{category}",})
    public Page<Restaurants> searchRestaurants(@PathVariable(required = false) String country,
                                               @PathVariable(required = false) String category,
                                               @RequestParam(defaultValue = "0") int page,
                                               @RequestParam(defaultValue = "10") int size){
        Pageable pageable = PageRequest.of(page,size);
        return restaurantsService.searchRestaurants(country, category ,pageable);
    }


    @PostMapping("/restaurants")
    public void test2(@RequestBody RestaurantsDto restaurantsDto){
        restaurantsService.saveRestaurants(restaurantsDto);
    }

//    {
//        "name": "IKIGAI 燒肉專門店",
//            "description": "燒肉店",
//            "address": "忠誠路二段55號B1",
//            "country": "臺北市",
//            "district": "士林區",
//            "score": 4.9,
//            "average": 500,
//            "image": "https://example.com/image.jpg",
//            "phone": "0228311698",
//            "store_id": 2,
//            "businessHours": [
//        {
//            "restaurantId": 1,
//                "dayOfWeek": "星期日",
//                "startTime": "11:30",
//                "endTime": "11:30"
//        },
//        {
//            "restaurantId": 1,
//                "dayOfWeek": "星期一",
//                "startTime":"11:30",
//                "endTime": "11:30"
//        }
//    ]
//    }
}
