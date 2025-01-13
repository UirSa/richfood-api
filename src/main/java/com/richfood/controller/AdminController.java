package com.richfood.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.richfood.model.Admin;
import com.richfood.service.AdminService;

@RestController
@RequestMapping("/Admin")
public class AdminController {
	
	@Autowired
	private AdminService adminService;
	
	@PostMapping("/registerAdmin")
	public void register(@RequestBody Admin admin) {
		adminService.registerAdmin(admin);
	
	}
	
	@PutMapping("/updateAdmin")
	public void update(@RequestBody Admin admin) {
		adminService.updateAdmin(admin);
	}
	
	@DeleteMapping("/deleteAdmin/{adminId}")
	public void delete(@PathVariable Integer adminId){
		adminService.deleteAdmin(adminId);
	}
	
	@GetMapping("/findAdmin")
	public List<String> readAll() {
		List<String> admins =  adminService.findAllAdmins();
		return admins;
	}

}
