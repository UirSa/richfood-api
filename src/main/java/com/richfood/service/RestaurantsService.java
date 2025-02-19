package com.richfood.service;

import com.richfood.dto.BusinessHoursDto;
import com.richfood.dto.RestaurantCategoriesDto;
import com.richfood.dto.RestaurantDto;
import com.richfood.model.*;
import com.richfood.repository.RestaurantCategoriesRepository;
import com.richfood.repository.RestaurantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantsService {
    @Autowired
    private RestaurantsRepository restaurantsRepository;
    @Autowired
    private RestaurantCategoriesRepository restaurantCategoriesRepository;


    //All restaurants
    public List<Restaurants> getAllRestaurants(){
        return restaurantsRepository.findAll();
    }
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
//        Optional<Restaurants> restaurant =restaurantsRepository.findById(restaurantId);
//        List<BusinessHours> businessHours=restaurant.get().getBusinessHours();
//        for (BusinessHours businessHour : businessHours) {
//            businessHour.getBusinessHoursId().getDayOfWeek();
//        }
       return restaurantsRepository.findById(restaurantId);
    }

    //Restaurants by lat and long for recommend
    public Page<Restaurants> getRestaurantByLatAndLong(Double latitude, Double longitude, Pageable pageable){
        Double distance=0.009*1;
        Double latMax =latitude+distance;
        Double latMin =latitude-distance;
        Double longMax=longitude+distance;
        Double longMin=longitude-distance;
        return restaurantsRepository.findRestaurantsByLatitudeAndLongitudeBetween(latMax,latMin,longMax,longMin,pageable);

    }

    //Save Restaurants for JSON
    public void saveRestaurantsAndBusinessHours(@RequestBody List<RestaurantDto> restaurantDtoList) {
        for (RestaurantDto restaurantDto : restaurantDtoList) {

            Restaurants restaurants = new Restaurants();
            restaurants.setName(restaurantDto.getName());
            restaurants.setDescription(restaurantDto.getDescription());
            restaurants.setCountry(restaurantDto.getCountry());
            restaurants.setDistrict(restaurantDto.getDistrict());
            restaurants.setAddress(restaurantDto.getAddress());
            restaurants.setLatitude(restaurantDto.getLatitude());
            restaurants.setLongitude(restaurantDto.getLongitude());
            restaurants.setScore(restaurantDto.getScore());
            restaurants.setAverage(restaurantDto.getAverage());
            restaurants.setImage(restaurantDto.getImage());
            restaurants.setPhone(restaurantDto.getPhone());
            restaurants.setStoreId(restaurantDto.getStoreId());

            restaurantsRepository.save(restaurants);
            List<BusinessHours> businessHoursList = new ArrayList<>();
            for (BusinessHoursDto businessHoursDto : restaurantDto.getBusinessHours()) {
                String startTime = businessHoursDto.getStartTime();
                String endTime = businessHoursDto.getEndTime();
                BusinessHoursId businessHoursId = new BusinessHoursId(businessHoursDto.getRestaurantId(), businessHoursDto.getDayOfWeek(), startTime);

                BusinessHours businessHours = new BusinessHours(businessHoursId, endTime);
                businessHoursList.add(businessHours);
            }
            restaurants.setBusinessHours(businessHoursList);
            restaurantsRepository.save(restaurants);
            List<RestaurantCategories> restaurantCategoriesList=new ArrayList<>();
            for (RestaurantCategoriesDto restaurantCategoriesDto: restaurantDto.getCategories()){
                Integer rid=restaurantCategoriesDto.getRestaurantId();
                Integer cid=restaurantCategoriesDto.getCategoryId();
                if (cid == null) {
                    throw new IllegalArgumentException("Category ID cannot be null for restaurant with ID: " + rid);
                }
                RestaurantCategoriesId restaurantCategoriesId=new RestaurantCategoriesId(rid,cid);

                RestaurantCategories restaurantCategories=new RestaurantCategories(restaurantCategoriesId);
                restaurantCategoriesList.add(restaurantCategories);
            }
            restaurants.setRestaurantCategories(restaurantCategoriesList);
            restaurantsRepository.save(restaurants);
            // 將所有 categories 一次性保存
//            if (!restaurantCategoriesList.isEmpty()) {
//                restaurantCategoriesRepository.saveAll(restaurantCategoriesList); // 假設你有 restaurantCategoriesRepository
//            }
            // 最後將所有的資料保存到 restaurants
//            restaurants.setRestaurantCategories(restaurantCategoriesList);
//            restaurantsRepository.save(restaurants);

        }
    }

    //Save englishRestaurants for JSON













    public Restaurants saveResraurantData(Integer restaurantId,String name,String country,String district,String address,String phone) {
    	Optional<Restaurants> optRestaurants=restaurantsRepository.findById(restaurantId);
    	Restaurants restaurantInfo=optRestaurants.get();
    	
    	if(name!=null) {
    		restaurantInfo.setName(name);
    	}
    	if(country!=null) {
    		restaurantInfo.setCountry(country);
    	}
    	if(district!=null) {
    		restaurantInfo.setDistrict(district);
    	}
    	if(address!=null) {
    		restaurantInfo.setAddress(address);
    	}
    	if(phone!=null) {
    		restaurantInfo.setPhone(phone);
    	}

    	return restaurantsRepository.save(restaurantInfo);

    }
}
