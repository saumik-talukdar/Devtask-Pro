package com.saumik.devtask_pro.user.dto;

import com.saumik.devtask_pro.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LoginResponse {
    private Long id;
    private String username;
    private String email;
    private Role role;
    private String message;
}
