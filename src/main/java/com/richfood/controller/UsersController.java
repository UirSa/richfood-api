package com.richfood.controller;


import java.time.LocalDate;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.richfood.model.Users;
import com.richfood.repository.UsersRepository;
import com.richfood.service.UsersService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/User")
public class UsersController {
	
	@Autowired UsersService userService;
	
	@Autowired
	private UsersRepository userRepository; 
	
	@PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	 public ResponseEntity<Map<String, String>> register(
	            @RequestParam("name") String name,
	            @RequestParam("userAccount") String userAccount,
	            @RequestParam("password") String password,
	            @RequestParam("tel") String tel,
	            @RequestParam("email") String email,
	            @RequestParam(value = "iconFile", required = false) MultipartFile iconFile,
	            @RequestParam("birthday") String birthday) {
	        try {
	            // 建立 Users 物件並設置值
	            Users user = new Users();
	            user.setName(name);
	            user.setUserAccount(userAccount);
	            user.setPassword(password);
	            user.setTel(tel);
	            user.setEmail(email);
	            user.setBirthday(LocalDate.parse(birthday)); // 假設日期格式為 ISO 格式

	            // 呼叫 Service 完成註冊邏輯
	            userService.registerMember(user, iconFile);

	            return ResponseEntity.ok(Map.of("message", "註冊成功"));
	        } catch (IllegalArgumentException e) {
	            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
	        } catch (Exception e) {
	            return ResponseEntity.status(500).body(Map.of("message", "伺服器錯誤：" + e.getMessage()));
	        }
	    }

	
	@PostMapping("/Userlogin")
	public ResponseEntity<Map<String, Object>> login(@RequestBody Users user, HttpServletRequest request) {
	    boolean isAuthenticated = userService.authenticate(user.getUserAccount(), user.getPassword());  // 這裡是 boolean

	    if (isAuthenticated) {
	        Optional<Users> optionalUser = userRepository.findByUserAccount(user.getUserAccount());
	        if (optionalUser.isPresent()) {
	            Users authenticatedUser = optionalUser.get();
	            Map<String, Object> response = new HashMap<>();
	            response.put("message", "登入成功");
	            response.put("userId", authenticatedUser.getUserId());  // 返回 userId
	            
	         // 將 userId 設置到 session 中
	            request.getSession().setAttribute("userId", authenticatedUser.getUserId());
	            
	            return ResponseEntity.ok(response);  // 成功登入，返回 userId
	        }
	    } 
	    // 如果帳號或密碼錯誤，返回錯誤訊息
	    Map<String, Object> errorResponse = new HashMap<>();
	    errorResponse.put("message", "帳號或密碼錯誤");
	    return ResponseEntity.badRequest().body(errorResponse);  // 返回錯誤訊息
	}
	
	@PostMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpServletRequest request) {
        try {
            userService.logout(request);
            return ResponseEntity.ok(Map.of("message", "登出成功"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("message", "登出失敗：" + e.getMessage()));
        }
    }
	 
	 @GetMapping("/getUserDetails")
	 public ResponseEntity<Users> getUserDetails(@RequestParam Integer userId) {
	     Users user = userService.getUserDetails(userId);
	     if (user != null) {
	         return ResponseEntity.ok(user);
	     } else {
	         return ResponseEntity.status(404).body(null);
	     }
	 }
	 
	 @PutMapping(value = "/updateUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	    public ResponseEntity<Map<String, Object>> updateUser(
	            HttpServletRequest request,
	            @RequestParam("userId") Integer userId,
	            @RequestParam(value = "name", required = false) String name,
	            @RequestParam(value = "tel", required = false) String tel,
	            @RequestParam(value = "email", required = false) String email,
	            @RequestParam(value = "birthday", required = false) String birthday,
	            @RequestParam(value = "iconFile", required = false) MultipartFile iconFile) {
	        try {
	            // 從 request 建立更新資料的 Users 物件
	            Users user = new Users();
	            user.setUserId(userId);
	            user.setName(name);
	            user.setTel(tel);
	            user.setEmail(email);
	            if (birthday != null && !birthday.isEmpty()) {
	                user.setBirthday(LocalDate.parse(birthday));
	            }

	            // 呼叫 Service 更新資料
	            userService.updateUser(request, user, iconFile);

	            Map<String, Object> response = new HashMap<>();
	            response.put("message", "資料更新成功");
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            Map<String, Object> errorResponse = new HashMap<>();
	            errorResponse.put("message", "更新失敗: " + e.getMessage());
	            return ResponseEntity.status(400).body(errorResponse);
	        }
	    }
	 
	 @DeleteMapping("/deleteUser/{userId}")
	 public ResponseEntity<String> deleteUser(@PathVariable Integer userId) {
	     try {
	         // 呼叫 UserService 來刪除會員，不需要重新排列ID
	         userService.deleteUserAndRearrangeIds(userId);
	         return ResponseEntity.ok("User deleted successfully.");
	     } catch (Exception e) {
	         return ResponseEntity.status(500).body("Error deleting user: " + e.getMessage());
	     }
	 }
}
