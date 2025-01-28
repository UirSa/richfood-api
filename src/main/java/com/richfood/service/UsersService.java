package com.richfood.service;



import com.richfood.model.Users;
import com.richfood.repository.UsersRepository;
import com.richfood.util.BCrypt;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;
    
    @Autowired
    private JavaMailSender mailSender; // Spring 提供的郵件發送工具
    
    // 存储 token 和过期时间
    private final ConcurrentHashMap<String, TokenInfo> resetTokens = new ConcurrentHashMap<>();
    
    private static final String IMAGE_DIRECTORY = System.getProperty("user.dir") + "/src/main/resources/static/UserImages/";
    //C:\Users\USER\eclipse-workspace\richfood\src\main\resources\static\UserImages

    
    public UsersService() {
        File directory = new File(IMAGE_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
    
    private void validateUserData(Users user) {
        if (usersRepository.findByName(user.getName().toLowerCase()).isPresent()) {
            throw new IllegalArgumentException("This name already exists");
        }
        if (usersRepository.findByEmail(user.getEmail().toLowerCase()).isPresent()) {
            throw new IllegalArgumentException("This Email already exists");
        }
        if (usersRepository.findByUserAccount(user.getUserAccount().toLowerCase()).isPresent()) {
            throw new IllegalArgumentException("This Account already exists");
        }
        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
    private Users findUserById(Integer userId) {
        return usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
    }

    
    public Users registerMember(Users user, MultipartFile iconFile) {
    	
    	 validateUserData(user);
    	
    	
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
            user.setPassword(null); // 移除密碼
            // 確保 gender 有效
            if (user.getGender() == null || user.getGender().isEmpty()) {
                user.setGender("other"); // 預設值為 "other"
            }
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
    public void changePassword(HttpServletRequest request, String newPassword) {
        // 從 Session 獲取用戶 ID
        Integer userId = (Integer) request.getSession().getAttribute("userId");
        if (userId == null) {
            throw new IllegalArgumentException("用戶未登入或會話已過期");
        }

        // 查詢用戶
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("用戶不存在"));

        // 更新為新密碼
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));

        // 保存修改
        usersRepository.save(user);
    }


    
    @Transactional
    public void deleteUser(Integer userId) {
    	 Users user = findUserById(userId); // 使用共用方法檢查用戶是否存在
    	    usersRepository.delete(user);
    	    System.out.println("User with id " + userId + " deleted successfully.");
    }
    

    // 生成临时 resetToken
    public String generateResetToken(String email) {
        // 生成唯一的 Token
        String resetToken = UUID.randomUUID().toString();
        // 设置过期时间（例如 30 分钟后）
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(30);

        // 存储到内存中
        resetTokens.put(resetToken, new TokenInfo(email, expiryTime));

        return resetToken;
    }
    
    // 验证 resetToken 是否有效
    public boolean validateResetToken(String resetToken) {
        TokenInfo tokenInfo = resetTokens.get(resetToken);
        if (tokenInfo == null || tokenInfo.getExpiryTime().isBefore(LocalDateTime.now())) {
            // Token 不存在或已过期
            resetTokens.remove(resetToken); // 清理无效 token
            return false;
        }
        return true;
    }

    // 根据 token 获取对应的邮箱
    public String getEmailByResetToken(String resetToken) {
        TokenInfo tokenInfo = resetTokens.get(resetToken);
        return tokenInfo != null ? tokenInfo.getEmail() : null;
    }

    // 移除 token
    public void removeResetToken(String resetToken) {
        resetTokens.remove(resetToken);
    }

    // 内部类用于存储 token 信息
    private static class TokenInfo {
        private final String email;
        private final LocalDateTime expiryTime;

        public TokenInfo(String email, LocalDateTime expiryTime) {
            this.email = email;
            this.expiryTime = expiryTime;
        }

        public String getEmail() {
            return email;
        }

        public LocalDateTime getExpiryTime() {
            return expiryTime;
        }
    }
    
    public void updatePasswordByEmail(String email, String newPassword) {
        // 查找用户
        Users user = usersRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("找不到該電子郵件的用戶"));

        // 使用 BCrypt 加密新密码
        String encodedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        // 更新密码
        user.setPassword(encodedPassword);

        // 保存用户
        usersRepository.save(user);
    }
    
    public void sendResetPasswordEmail(String email, String resetLink) {
        try {
            // 创建邮件内容
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email); // 接收者邮箱
            message.setSubject("重置您的密码"); // 邮件标题
            message.setText("点击以下链接以重置您的密码：\n" + resetLink); // 邮件正文

            // 发送邮件
            mailSender.send(message);
            System.out.println("重置密码邮件已发送到：" + email);
        } catch (Exception e) {
            throw new RuntimeException("发送邮件失败", e);
        }
    }
       
}