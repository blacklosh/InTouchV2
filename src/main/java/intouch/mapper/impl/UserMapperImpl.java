package ru.itis.intouch.mapper.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.intouch.dto.SignUpForm;
import ru.itis.intouch.dto.UserDto;
import ru.itis.intouch.mapper.UserMapper;
import ru.itis.intouch.model.User;
import ru.itis.intouch.services.PasswordEncoder;

import java.util.UUID;

@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public User toUser(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .name(dto.getName())
                .build();
    }

    @Override
    public User toUser(SignUpForm dto) {
        return User.builder()
                .id(UUID.randomUUID())
                .name(dto.getName())
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .build();
    }
}
