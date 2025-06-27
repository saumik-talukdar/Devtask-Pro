package com.saumik.devtask_pro.user.dto;

import com.saumik.devtask_pro.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SignupResponse {
    private String username;
    private String email;
    private Role role;
}
