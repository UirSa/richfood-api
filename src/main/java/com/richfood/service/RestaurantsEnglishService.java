package com.richfood.service;

import com.richfood.model.*;
import com.richfood.repository.RestaurantsEnglishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Optionals;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class RestaurantsEnglishService {
    @Autowired
    private RestaurantsEnglishRepository restaurantsEnglishRepository;

    //Restaurants list by country and category for checkbox and selected
    public Page<RestaurantsEnglish> searchRestaurantsEnglishByCountryAndCategory(String country, String category, Pageable pageable) {
        if (country != null && category != null) {
            return restaurantsEnglishRepository.findRestaurantsEnglishByCountryAndCategoryName(country, category, pageable);
        } else if (country != null) {
            return restaurantsEnglishRepository.findRestaurantsEnglishByCountry(country, pageable);
        } else if (category != null) {
            return restaurantsEnglishRepository.findRestaurantsEnglishByCategoryName(category, pageable);
        } else {
            return restaurantsEnglishRepository.findAll(pageable);
        }
    }

    //Restaurants by id
    public Optional<RestaurantsEnglish> getRestaurantsEnglishById(Integer restaurantId) {
        Optional<RestaurantsEnglish> restaurantOptional = restaurantsEnglishRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            RestaurantsEnglish restaurantsEnglish = restaurantOptional.get();

            List<BusinessHoursEnglish> businessHoursEnglishes = restaurantsEnglish.getBusinessHoursEnglishes();
            List<BusinessHoursEnglish> businessHoursEnglishList = new ArrayList<>();
            for (BusinessHoursEnglish businessHour : businessHoursEnglishes) {
                String startTime = businessHour.getBusinessHoursId().getStartTime();
                String endTime = businessHour.getEndTime();
                businessHour.getBusinessHoursId().getDayOfWeek();
                BusinessHoursId businessHoursId = new BusinessHoursId(businessHour.getBusinessHoursId().getRestaurantId(), businessHour.getBusinessHoursId().getDayOfWeek(), startTime);
                BusinessHoursEnglish businessHoursEnglish = new BusinessHoursEnglish(businessHoursId, endTime);

                businessHoursEnglishList.add(businessHoursEnglish);
            }
            restaurantsEnglish.setBusinessHoursEnglishes(businessHoursEnglishList);

            return Optional.of(restaurantsEnglish);
        }
            return Optional.empty();
        }

    //Restaurants by lat and long for recommend
    public Optional<RestaurantsEnglish> getRestaurantByLatAndLong(@RequestParam(name = "lat",required = true) Double latitude,
                                                           @RequestParam(name = "long",required = true) Double longitude){
        Double distance=0.009*10;
        Double latMax =latitude+distance;
        Double latMin =latitude-distance;
        Double longMax=longitude+distance;
        Double longMin=longitude-distance;
        List<RestaurantsEnglish> restaurantsList =restaurantsEnglishRepository.findRestaurantsEnglishByLatitudeAndLongitudeBetween(latMax,latMin,longMax,longMin);

        if (restaurantsList.isEmpty()) {
            return Optional.empty();  // 若沒有符合條件的餐廳，返回空的 Optional
        }

        List<RestaurantsEnglish> highRatedRestaurants = restaurantsList.stream()
                .filter(r -> r.getScore() > 3.0)  // 假設評分大於 4.0 的餐廳
                .collect(Collectors.toList());
        if (highRatedRestaurants.isEmpty()) {
            return Optional.empty();
        }


        // 計算所有餐廳的總權重（評分之和）
        double totalWeight = highRatedRestaurants.stream()
                .mapToDouble(RestaurantsEnglish::getScore)
                .sum();

        Random random = new Random();
        double randomValue = random.nextDouble() * totalWeight;  // 隨機選擇一個點，範圍在 [0, totalWeight) 之間

        // 根據加權隨機選擇餐廳
        double cumulativeWeight = 0;
        for (RestaurantsEnglish restaurant : highRatedRestaurants) {
            cumulativeWeight += restaurant.getScore();  // 累積權重
            if (cumulativeWeight >= randomValue) {
                return Optional.of(restaurant);  // 找到加權隨機選擇的餐廳
            }
        }

        return Optional.empty();  // 如果沒有選中（理論上不會到達這一步）
    }
}
