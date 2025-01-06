package com.richfood.service;

import com.richfood.repository.RestaurantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantsService {
    @Autowired
    private RestaurantsRepository restaurantsRepository;



}
