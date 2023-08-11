package com.system.Gulafi.service;


import com.system.Gulafi.dto.UserDto;
import com.system.Gulafi.entity.User;

import java.util.Optional;

public interface UserService {

    void register(UserDto userDto) ;

    Optional<User> getActiveUser();


}
