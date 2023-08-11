package com.system.Gulafi.service.impl;


import com.system.Gulafi.config.PasswordEncoderUtil;
import com.system.Gulafi.dto.UserDto;
import com.system.Gulafi.entity.User;
import com.system.Gulafi.repo.UserRepo;
import com.system.Gulafi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;

    @Override
    public void register(UserDto userDto) {
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setPassword(PasswordEncoderUtil.getInstance().encode(userDto.getPassword()));
        user.setName(userDto.getName());
        user.setRole("USER");
        userRepo.save(user);
    }

    @Override
    public Optional<User> getActiveUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepo.findByEmail(authentication.getName());
    }
}
