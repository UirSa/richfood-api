package com.richfood.service;

import com.richfood.model.Restaurants;
import com.richfood.model.RestaurantsEnglish;
import com.richfood.repository.RestaurantsEnglishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RestaurantsEnglishService {
    @Autowired
    private RestaurantsEnglishRepository restaurantsEnglishRepository;

    //Restaurants list by country and category for checkbox and selected
    public Page<RestaurantsEnglish> searchRestaurantsEnglishByCountryAndCategory(String country, String category, Pageable pageable){
        if(country !=null && category !=null){
            return restaurantsEnglishRepository.findRestaurantsEnglishByCountryAndCategoryName(country, category, pageable);
        }else if(country !=null){
            return restaurantsEnglishRepository.findRestaurantsEnglishByCountry(country, pageable);
        }else if(category !=null){
            return restaurantsEnglishRepository.findRestaurantsEnglishByCategoryName(category,pageable);
        }else {
            return restaurantsEnglishRepository.findAll(pageable);
        }
    }

}
