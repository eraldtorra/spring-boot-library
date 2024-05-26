package com.code.springbootlibrary.controller;

import com.code.springbootlibrary.responsemodels.ProfileResponse;
import com.code.springbootlibrary.service.ProfileService;
import com.code.springbootlibrary.utils.ExtractJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://localhost:3000")
@RequestMapping("/api/profile/secure")
public class ProfilesController {

    private ProfileService profileService;

    @Autowired
    public ProfilesController(ProfileService profileService) {
        this.profileService = profileService;
    }



    @GetMapping("/get")
    public ProfileResponse getProfile(@RequestHeader(value = "Authorization") String token) {

        String user = ExtractJWT.payloadJWTExtraction(token, "\"uid\"");

        return profileService.getUser(user);

    }


}
