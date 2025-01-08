package com.richfood.service;

import com.richfood.dto.BusinessHoursDto;
import com.richfood.dto.RestaurantsDto;
import com.richfood.model.BusinessHours;
import com.richfood.model.BusinessHoursId;
import com.richfood.model.Restaurants;
import com.richfood.repository.RestaurantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public List<Restaurants> searchRestaurants(){
        return restaurantsRepository.findAll();

    }

    public void saveRestaurants(@RequestBody RestaurantsDto restaurantsDto){
        Restaurants restaurants = new Restaurants();
        restaurants.setName(restaurantsDto.getName());
        restaurants.setDescription(restaurantsDto.getDescription());
        restaurants.setCountry(restaurantsDto.getCountry());
        restaurants.setDistrict(restaurantsDto.getDistrict());
        restaurants.setAddress(restaurantsDto.getAddress());
        restaurants.setScore(restaurantsDto.getScore());
        restaurants.setAverage(restaurantsDto.getAverage());
        restaurants.setImage(restaurantsDto.getImage());
        restaurants.setPhone(restaurantsDto.getPhone());
        restaurants.setStoreId(restaurantsDto.getStoreId());

        List<BusinessHours> businessHoursList=new ArrayList<>();
        for (BusinessHoursDto businessHoursDto : restaurantsDto.getBusinessHours()) {
            LocalTime startTime = LocalTime.parse(businessHoursDto.getStartTime()); // "11:00" 會被轉換為 LocalTime.of(11, 0)
            //TODO 改格式
            BusinessHoursId businessHoursId=new BusinessHoursId(businessHoursDto.getRestaurantId(),businessHoursDto.getDayOfWeek(), businessHoursDto.getStartTime());

            BusinessHours businessHours = new BusinessHours(businessHoursId, businessHoursDto.getEndTime());
            businessHoursList.add(businessHours);
        }
        restaurants.setBusinessHours(businessHoursList);

        restaurantsRepository.save(restaurants);
    }

}
