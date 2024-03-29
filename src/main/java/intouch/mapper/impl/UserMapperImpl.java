package intouch.mapper.impl;

import intouch.dto.SignUpForm;
import intouch.dto.UserDto;
import intouch.mapper.UserMapper;
import intouch.model.User;
import intouch.services.PasswordEncoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserMapperImpl implements UserMapper {
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .avatarId(user.getAvatarId())
                .build();
    }

    @Override
    public User toUser(UserDto dto) {
        return User.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .name(dto.getName())
                .avatarId(dto.getAvatarId())
                .build();
    }

    @Override
    public User toUser(SignUpForm dto) {
        return User.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .passwordHash(passwordEncoder.encode(dto.getPassword()))
                .build();
    }
}
