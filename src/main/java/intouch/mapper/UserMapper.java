package intouch.mapper;

import intouch.dto.SignUpForm;
import intouch.dto.UserDto;
import intouch.model.User;

public interface UserMapper {
    UserDto toDto(User user);
    User toUser(UserDto dto);
    User toUser(SignUpForm dto);
}
