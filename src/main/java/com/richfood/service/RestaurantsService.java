package com.richfood.service;

import com.richfood.dto.BusinessHoursDto;
import com.richfood.dto.RestaurantsDto;
import com.richfood.model.BusinessHours;
import com.richfood.model.BusinessHoursId;
import com.richfood.model.Restaurants;
import com.richfood.repository.RestaurantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantsService {
    @Autowired
    private RestaurantsRepository restaurantsRepository;


    //Restaurants list by country and category for checkbox and selected
    public  Page<Restaurants> searchRestaurantsByCountryAndCategory(String country, String category, Pageable pageable){
        if(country !=null && category !=null){
           return restaurantsRepository.findRestaurantsByCountryAndCategoryName(country, category, pageable);
        }else if(country !=null){
            return restaurantsRepository.findRestaurantsByCountry(country, pageable);
        }else if(category !=null){
            return restaurantsRepository.findRestaurantsByCategoryName(category,pageable);
        }else {
            return restaurantsRepository.findAll(pageable);
        }
    }

    //Restaurants by id
    public Optional<Restaurants> getRestaurantsById(Integer restaurantId){
       return restaurantsRepository.findById(restaurantId);
    }

    //Restaurants by lat and long for recommend
    public Page<Restaurants> getRestaurantByLatAndLong(Double latitude, Double longitude, Pageable pageable){
        Double distance=0.009*5;
        Double latMax =latitude+distance;
        Double latMin =latitude-distance;
        Double longMax=longitude+distance;
        Double longMin=longitude-distance;
        return restaurantsRepository.findRestaurantsByLatitudeAndLongitudeBetween(latMax,latMin,longMax,longMin,pageable);

    }

    //Save Restaurants for JSON
    public void saveRestaurants(@RequestBody RestaurantsDto restaurantsDto){
        Restaurants restaurants = new Restaurants();
        restaurants.setName(restaurantsDto.getName());
        restaurants.setDescription(restaurantsDto.getDescription());
        restaurants.setCountry(restaurantsDto.getCountry());
        restaurants.setDistrict(restaurantsDto.getDistrict());
        restaurants.setAddress(restaurantsDto.getAddress());
        restaurants.setLatitude(restaurantsDto.getLatitude());
        restaurants.setLongitude(restaurantsDto.getLongitude());
        restaurants.setScore(restaurantsDto.getScore());
        restaurants.setAverage(restaurantsDto.getAverage());
        restaurants.setImage(restaurantsDto.getImage());
        restaurants.setPhone(restaurantsDto.getPhone());
        restaurants.setStoreId(restaurantsDto.getStoreId());

        restaurantsRepository.save(restaurants);
        List<BusinessHours> businessHoursList=new ArrayList<>();
        for (BusinessHoursDto businessHoursDto : restaurantsDto.getBusinessHours()) {
            String startTime = businessHoursDto.getStartTime();
            String endTime = businessHoursDto.getEndTime();
            //TODO 改格式
            BusinessHoursId businessHoursId=new BusinessHoursId(businessHoursDto.getRestaurantId(),businessHoursDto.getDayOfWeek(), startTime);

            BusinessHours businessHours = new BusinessHours(businessHoursId, endTime);
            businessHoursList.add(businessHours);
        }
        restaurants.setBusinessHours(businessHoursList);

        restaurantsRepository.save(restaurants);
    }

}
