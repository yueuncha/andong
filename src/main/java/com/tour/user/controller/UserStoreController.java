package com.tour.user.controller;

import com.tour.JsonForm;
import com.tour.user.service.UserStoreServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/store")
@Controller
public class UserStoreController {

    private final UserStoreServiceImpl storeService;

    @Autowired
    public UserStoreController(UserStoreServiceImpl storeService) {
        this.storeService = storeService;
    }



}
