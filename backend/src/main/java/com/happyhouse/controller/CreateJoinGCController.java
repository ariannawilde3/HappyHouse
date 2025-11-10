package com.happyhouse.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.happyhouse.model.SettingsForm;


@RestController
@RequestMapping("/api/gcc")
public class CreateJoinGCController {


    @PostMapping("/")
    public SettingsForm getSettings(@RequestBody SettingsForm form) {
        // save to database tbd 
        return new SettingsForm(form.getRoomieCount(), form.getHouseName()); 
    }


    
}