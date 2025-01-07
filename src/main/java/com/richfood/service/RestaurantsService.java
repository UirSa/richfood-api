package com.richfood.service;

import com.richfood.model.Restaurants;
import com.richfood.repository.RestaurantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantsService {
    @Autowired
    private RestaurantsRepository restaurantsRepository;

    public List<Restaurants> searchRestaurants(){
        return restaurantsRepository.findAll();

    }

}
