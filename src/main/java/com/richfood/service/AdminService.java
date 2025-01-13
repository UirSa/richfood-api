package com.richfood.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.richfood.model.Admin;
import com.richfood.repository.AdminRepository;
import com.richfood.util.BCrypt;

@Service
public class AdminService {

	@Autowired
	private AdminRepository adminRepository;
	
	public Admin registerAdmin(Admin admin) {
		
		// 檢查帳號是否已存在
        if (adminRepository.existsByAdminAccount(admin.getAdminAccount())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin account already exists");
        }
		
		admin.setPassword(BCrypt.hashpw(admin.getPassword(), BCrypt.gensalt()));
		
        return adminRepository.save(admin);
	}
	
	public Admin updateAdmin(Admin admin) {
        // 驗證管理者是否存在
        Admin existingAdmin = adminRepository.findById(admin.getAdminId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        existingAdmin.setAdminAccount(admin.getAdminAccount());
        existingAdmin.setPassword(admin.getPassword());

        return adminRepository.save(existingAdmin);
    }
	
	public void deleteAdmin(Integer adminId) {
        // 驗證管理者是否存在
        Admin existingAdmin = adminRepository.findById(adminId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));

        adminRepository.delete(existingAdmin);
    }
	
	public List<String> findAllAdmins() {
        return adminRepository.findAllAdminAccount();
    }
	
}
