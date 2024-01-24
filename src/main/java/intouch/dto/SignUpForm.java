package ru.itis.intouch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class SignUpForm {
    private String name;
    private String email;
    private String password;
}
