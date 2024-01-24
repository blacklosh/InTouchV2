package ru.itis.intouch.mapper;

import ru.itis.intouch.dto.SignUpForm;
import ru.itis.intouch.dto.UserDto;
import ru.itis.intouch.model.User;

public interface UserMapper {
    UserDto toDto(User user);
    User toUser(UserDto dto);
    User toUser(SignUpForm dto);
}
