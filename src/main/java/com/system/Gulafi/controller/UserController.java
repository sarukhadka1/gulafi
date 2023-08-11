package com.system.Gulafi.controller;

import com.system.Gulafi.dto.UserDto;
import com.system.Gulafi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/account")
    public String login(){
        return "account";
    }

    @PostMapping("/register")
    public String register(UserDto userDto){
        userService.register(userDto);
        return "redirect:/user/account?success";
    }

    @PostMapping("/login")
    public String login(UserDto userDto){
        return "redirect:/user/account";
    }
}
