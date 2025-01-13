package com.richfood.service;



import com.richfood.model.Users;
import com.richfood.repository.UsersRepository;
import com.richfood.util.BCrypt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;



@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    
    public Users registerMember(Users user) {
        // 確保名稱不重複，忽略大小寫
        if (usersRepository.findByName(user.getName().toLowerCase()).isPresent()) {
            throw new IllegalArgumentException("This name already exists");
        }
        // 確保Email不重複，忽略大小寫
        if (usersRepository.findByEmail(user.getEmail().toLowerCase()).isPresent()) {
        	throw new IllegalArgumentException("This Email already exists");
        }

        // 確保帳號不重複，忽略大小寫
        if (usersRepository.findByUserAccount(user.getUserAccount().toLowerCase()).isPresent()) {
            throw new IllegalArgumentException("This Account already exists");
        }

        // 設定用戶資料
        user.setName(user.getName());
        user.setUserAccount(user.getUserAccount());
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        user.setTel(user.getTel());
        user.setEmail(user.getEmail());
        user.setIcon(user.getIcon()); // 處理圖片
        user.setBirthday(user.getBirthday()); // 處理生日

        // 儲存並返回使用者資料
        return usersRepository.save(user);
    }

    
    public boolean authenticate(String userAccount, String password) {
        Optional<Users> optionalUser = usersRepository.findByUserAccount(userAccount);

        if (optionalUser.isPresent()) {
            Users user = optionalUser.get();
            return BCrypt.checkpw(password, user.getPassword());  // 比對密碼
        }

        return false;  // 如果用戶不存在，返回 false
    }
    
    @Transactional
    public void logout(HttpServletRequest request) {
        request.getSession().invalidate();
        System.out.println("用戶已成功登出");
    }

    
    public Users getUserDetails(Integer userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (userOptional.isPresent()) {
            Users user = userOptional.get();
            user.setPassword(null);  // 移除密碼
            return user;
        }
        return null; // 用戶不存在時返回 null
    }
    
    // 更新用戶資料
    @Transactional
    public void updateUser(HttpServletRequest request, Users user) {
        // 從 session 中獲取當前登入用戶的 userId
    	Integer userId = (Integer) request.getSession().getAttribute("userId");
        System.out.println("From session: userId = " + userId);  // 確認 userId 是否正確
        if (userId == null) {
            throw new IllegalArgumentException("用戶未登入或會話過期");
        }

        // 根據 userId 查找當前用戶
        Optional<Users> existingUserOptional = usersRepository.findById(userId);

        if (existingUserOptional.isPresent()) {
            Users existingUser = existingUserOptional.get();

            // 更新資料
            if (user.getName() != null && !user.getName().isEmpty()) {
                existingUser.setName(user.getName());
            }
            if (user.getTel() != null && !user.getTel().isEmpty()) {
                existingUser.setTel(user.getTel());
            }
            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            	existingUser.setEmail(user.getEmail());
            }
            if (user.getIcon() != null) {
                existingUser.setIcon(user.getIcon());
            }
            if (user.getBirthday() != null) {
                existingUser.setBirthday(user.getBirthday());
            }

            // 儲存更新後的資料
            usersRepository.save(existingUser);
        } else {
            throw new IllegalArgumentException("用戶不存在");
        }
    } 
    
    @Transactional
    public void deleteUserAndRearrangeIds(Integer userId) {
        // 刪除指定ID的會員
        usersRepository.deleteById(userId);
        
        // 不需要修改ID，ID不會變動
        
    }
       
}