package intouch.services;

import intouch.dto.UserDto;

import java.util.UUID;

public interface UsersService {
    UserDto getById(UUID id);
    void delete(UUID id);
}
