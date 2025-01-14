package com.richfood.service;



import com.richfood.model.Users;
import com.richfood.repository.UsersRepository;
import com.richfood.util.BCrypt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    
    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/images/";
    //C:\Users\USER\eclipse-workspace\richfood\src\main\resources\static\images

    
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

        // 如果圖片有上傳，保存圖片文件
        if (iconFile != null && !iconFile.isEmpty()) {
            // 當用戶使用 MultipartFile 上傳圖片
            String fileName = saveIconFile(iconFile);
            user.setIcon("/images/" + fileName);
        } else if (user.getIcon() != null && user.getIcon().startsWith("data:/images/")) {
            // 當用戶使用 Base64 上傳圖片
            String fileName = saveBase64IconToFile(user.getIcon());
            user.setIcon("/images/" + fileName);
        } else {
            // 當用戶沒有上傳圖片，分配預設頭像
            int randomAvatar = (int) (Math.random() * 2) + 1;
            user.setIcon(randomAvatar == 1 ? "/images/avatar01.jpg" : "/images/avatar02.jpg");
        }



        // 儲存並返回使用者資料
        return usersRepository.save(user);
    }
    
    private String saveBase64IconToFile(String base64Icon) {
        // 檢查 Base64 字串是否為空或過長
        if (base64Icon == null || base64Icon.isEmpty()) {
            throw new IllegalArgumentException("Base64 string is null or empty");
        }
//        if (base64Icon.length() > 5000) {
//            throw new IllegalArgumentException("Base64 string is too long");
//        }

        // 驗證 Base64 是否有開頭資訊，例如 "data:image/png;base64,"
        if (!base64Icon.startsWith("data:image/")) {
            throw new IllegalArgumentException("Provided string is not a valid Base64 encoded image");
        }

        // 分割 Base64 資料，提取文件類型與實際 Base64 數據
        String[] parts = base64Icon.split(",");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid Base64 image data");
        }
        String base64Image = parts[1]; // 實際的 Base64 數據
        String fileType = parts[0].split(";")[0].split("/")[1]; // 提取文件類型 (png, jpg, etc.)

        // 驗證文件類型是否支持（可根據需求擴展類型）
        if (!fileType.matches("png|jpg|jpeg|gif")) {
            throw new IllegalArgumentException("Unsupported image file type: " + fileType);
        }

        // 生成唯一檔案名稱
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "." + fileType;

        // 檢查目錄是否存在，若不存在則創建
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new RuntimeException("Failed to create directory: " + IMAGE_DIRECTORY);
        }

        // 將 Base64 解碼為二進位數據並寫入文件
        File outputFile = new File(directory, fileName);
        try {
            byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
            Files.write(outputFile.toPath(), decodedBytes);
            System.out.println("Image saved successfully to: " + outputFile.getAbsolutePath());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Base64 decoding failed", e);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }
        System.out.println("Saved file name: " + fileName);
        System.out.println("Full path: " + outputFile.getAbsolutePath());
        // 返回檔案的相對路徑（存入資料庫的值）
        return fileName;
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
        if (cleanedFileName.length() > 10) {
            cleanedFileName = cleanedFileName.substring(0, 10);
        }

        // 生成唯一檔案名稱
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + "_" + cleanedFileName;

        // 確保目錄存在
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new RuntimeException("Failed to create directory: " + IMAGE_DIRECTORY);
        }

        // 保存檔案
        File dest = new File(directory, fileName);
        try {
            iconFile.transferTo(dest);
            System.out.println("Image saved successfully to: " + dest.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save image file", e);
        }

        // 返回相對路徑（存入資料庫的值）
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
    public void updateUser(HttpServletRequest request, Users user) {
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        System.out.println("From session: userId = " + userId);

        if (userId == null) {
            throw new IllegalArgumentException("用戶未登入或會話過期");
        }

        Optional<Users> existingUserOptional = usersRepository.findById(userId);
        if (existingUserOptional.isPresent()) {
            Users existingUser = existingUserOptional.get();

            // 更新其他資料
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

            // 處理圖片更新
            if (user.getIcon() != null && !user.getIcon().isEmpty()) {
                // 先預設不做轉檔，直接存
                String newIconPath = user.getIcon();

                // 如果要特別處理 Base64 才改 newIconPath
                if (user.getIcon().startsWith("data:image/")) {
                    // 執行 Base64 解碼，產生實際檔案
                    newIconPath = "/images/" + saveBase64IconToFile(user.getIcon());
                }

                // 其餘情況不丟錯誤，不拋例外，直接存原字串
                existingUser.setIcon(newIconPath);
            } else {
                System.out.println("No icon provided, keeping the existing icon.");
            }
        }
        
    }
     



    
    @Transactional
    public void deleteUserAndRearrangeIds(Integer userId) {
        // 刪除指定ID的會員
        usersRepository.deleteById(userId);
        
        // 不需要修改ID，ID不會變動
        
    }
       
}