package intouch.services.impl;

import intouch.dto.UserDto;
import intouch.exceptions.NotFoundException;
import intouch.mapper.UserMapper;
import intouch.model.User;
import intouch.repository.UsersRepository;
import intouch.services.UsersService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto getById(UUID id) {
        Optional<User> optionalUser = usersRepository.findById(id);
        if(optionalUser.isEmpty()) {
            throw new NotFoundException("USER WITH ID " + id + " NOT FOUND");
        }
        User user = optionalUser.get();
        return userMapper.toDto(user);
    }

    @Override
    public void delete(UUID id) {
        usersRepository.delete(id);
    }
}
