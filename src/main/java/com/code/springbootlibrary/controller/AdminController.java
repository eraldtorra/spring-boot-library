package com.code.springbootlibrary.controller;

import com.code.springbootlibrary.requestmodels.AddBookRequest;
import com.code.springbootlibrary.service.AdminService;
import com.code.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }


    @PostMapping("/secure/add/book")
    public void postBook(@RequestHeader(value = "Authorization") String token,
                         @RequestBody AddBookRequest addBookRequest) {

        String admin = ExtractJWT.payloadJWTExtraction(token, "\"userType\"");
        if (!admin.equals("admin")){
            throw new RuntimeException("Unauthorized");
        }

        adminService.postBook(addBookRequest);
    }
}
