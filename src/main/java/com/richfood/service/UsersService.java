package com.richfood.service;



import com.richfood.model.Users;
import com.richfood.repository.UsersRepository;
import com.richfood.util.BCrypt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    
    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/UserImages/";
    //C:\Users\USER\eclipse-workspace\richfood\src\main\resources\static\UserImages

    
    public UsersService() {
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    public Users registerMember(Users user, MultipartFile iconFile) {
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
        user.setBirthday(user.getBirthday()); // 處理生日
        user.setGender(user.getGender());

        // 如果圖片有上傳，保存圖片文件
        if (iconFile != null && !iconFile.isEmpty()) {
            String fileName = saveIconFile(iconFile);
            user.setIcon("/UserImages/" + fileName);
        } else {
            // 分配預設頭像
            int randomAvatar = (int) (Math.random() * 2) + 1;
            user.setIcon(randomAvatar == 1 
                ? "/UserImages/avatar01.jpg" 
                : "/UserImages/avatar02.jpg");
        }



        // 儲存並返回使用者資料
        return usersRepository.save(user);
    }
    
    private String saveIconFile(MultipartFile iconFile) {
        if (iconFile == null || iconFile.isEmpty()) {
            throw new RuntimeException("Uploaded file is empty or missing");
        }

        // 獲取原始檔案名稱
        String originalFileName = iconFile.getOriginalFilename();
        if (originalFileName == null) {
            throw new RuntimeException("Uploaded file has no name");
        }

        // 清理檔案名稱並限制長度
        String cleanedFileName = originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-_]", "_");
        if (cleanedFileName.length() > 50) {
            cleanedFileName = cleanedFileName.substring(0, 50);
        }

        // 生成唯一檔案名稱
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + cleanedFileName;

        // 確保目錄存在
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new RuntimeException("Failed to create directory: " + IMAGE_DIRECTORY);
        }

     // 儲存到檔案系統
        File dest = new File(directory, fileName);
        try {
            iconFile.transferTo(dest);
            System.out.println("Image saved successfully to: " + dest.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }

        // 回傳檔案名稱給上層
        return fileName;
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
    
    @Transactional
    public void updateUser(HttpServletRequest request, Users user, MultipartFile iconFile) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        System.out.println("From session: userId = " + userId);

        if (userId == null) {
            throw new IllegalArgumentException("用戶未登入或會話過期");
        }

        Optional<Users> existingUserOptional = usersRepository.findById(userId);
        if (existingUserOptional.isEmpty()) {
            throw new IllegalArgumentException("用戶不存在");
        }

        Users existingUser = existingUserOptional.get();

        // 更新可更新的欄位
        if (user.getName() != null && !user.getName().isEmpty()) {
            existingUser.setName(user.getName());
        }
        if (user.getTel() != null && !user.getTel().isEmpty()) {
            existingUser.setTel(user.getTel());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            existingUser.setEmail(user.getEmail());
        }
        if (user.getBirthday() != null) {
            existingUser.setBirthday(user.getBirthday());
        }
        if (user.getGender() != null && !user.getGender().isEmpty()) {
            existingUser.setGender(user.getGender()); // 更新性別
        }

        // 如果有新圖檔，就覆蓋舊的 icon
        if (iconFile != null && !iconFile.isEmpty()) {
            String fileName = saveIconFile(iconFile);
            existingUser.setIcon("/UserImages/" + fileName);
        } else {
            System.out.println("No new icon file provided, keeping existing icon.");
        }

        // 如果有需要更新密碼，也可以在這裡做
        // if (user.getPassword() != null && !user.getPassword().isEmpty()) {
        //     existingUser.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        // }

        // 最後存入資料庫
        usersRepository.save(existingUser);
    }
     



    
    @Transactional
    public void deleteUserAndRearrangeIds(Integer userId) {
        // 刪除指定ID的會員
        usersRepository.deleteById(userId);
        
        // 不需要修改ID，ID不會變動
        
    }
       
}