package com.richfood.controller;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.richfood.model.FavoriteRestaurants;
import com.richfood.model.Restaurants;
import com.richfood.service.FavoriteRestaurantService;

@Controller
@RequestMapping("/favorite")
public class FavoriteRestaurantController {

    @Autowired
    private FavoriteRestaurantService favoriteRestaurantService;

    // 添加收藏
    @PostMapping
    public ResponseEntity<String> addFavorite(@RequestBody FavoriteRestaurants favoriteRestaurant, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId"); // 從 session 獲取 userId
        if (userId == null) {
            return ResponseEntity.status(401).body("未登入");
        }
        favoriteRestaurantService.addFavorite(userId, favoriteRestaurant.getRestaurantId());
        return ResponseEntity.ok("餐廳已加入收藏");
    }

    // 移除收藏
    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> removeFavorite(@PathVariable Integer restaurantId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId"); // 從 session 獲取 userId
        if (userId == null) {
            return ResponseEntity.status(401).body("未登入");
        }
        
        boolean removed = favoriteRestaurantService.removeFavorite(userId, restaurantId);
        
        if (!removed) {
            return ResponseEntity.status(404).body("收藏不存在");
        }
        
        return ResponseEntity.ok("餐廳已從收藏中移除");
    }


    // 查詢已收藏的餐廳
    @GetMapping
    public ResponseEntity<List<Restaurants>> getFavoriteRestaurants(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("Session userId: " + userId); // 添加日誌
        if (userId == null) {
            return ResponseEntity.status(401).body(null); // 未登入
        }
        List<Restaurants> favoriteRestaurants = favoriteRestaurantService.getFavoriteRestaurants(userId);
        return ResponseEntity.ok(favoriteRestaurants);
    }

    // 查詢收藏餐廳及其詳細信息
    @GetMapping("/restaurants")
    public ResponseEntity<List<Map<String, Object>>> getFavoriteRestaurantsWithDetails(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        System.out.println("Session 中的 userId: " + userId); // 確認 userId 是否從 session 中正確讀取
        if (userId == null) {
            return ResponseEntity.status(401).body(null); // 未登入
        }
        return ResponseEntity.ok(favoriteRestaurantService.getFavoriteRestaurantsWithDetails(userId));
    }
    
    
    @GetMapping("/favoriteRestaurantCount/{restaurantId}")
    public ResponseEntity<Integer>selectFavorite(@PathVariable Integer restaurantId) {
    	List<FavoriteRestaurants> favoriteRestaurant=favoriteRestaurantService.selectFavorite(restaurantId);
    	Integer favoriteRestaurantCount=favoriteRestaurant.size();
    	System.out.println(favoriteRestaurantCount);
    	
    	return ResponseEntity.ok(favoriteRestaurantCount);
    }
    
    @GetMapping("/{restaurantId}")
    public ResponseEntity<Boolean> isRestaurantFavorited(@PathVariable Integer restaurantId, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body(false);
        }
        boolean isFavorited = favoriteRestaurantService.isFavorite(userId, restaurantId);
        return ResponseEntity.ok(isFavorited);
    }

}
