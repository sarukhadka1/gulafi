package com.system.Gulafi.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {
    private int id;
    private String name;
    private String password;
    private String email;
    private String role;
}
