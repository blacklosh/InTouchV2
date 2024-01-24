package ru.itis.intouch.services.impl;

import lombok.RequiredArgsConstructor;
import ru.itis.intouch.dto.SignInForm;
import ru.itis.intouch.dto.SignUpForm;
import ru.itis.intouch.dto.UserDto;
import ru.itis.intouch.exceptions.InTouchException;
import ru.itis.intouch.mapper.UserMapper;
import ru.itis.intouch.model.User;
import ru.itis.intouch.repository.UsersRepository;
import ru.itis.intouch.services.AuthorizationService;
import ru.itis.intouch.services.PasswordEncoder;

import java.util.Optional;

@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(SignUpForm form) throws InTouchException {
        if (form.getEmail() == null) {
            throw new InTouchException("Email cannot be null");
        }
        Optional<User> optionalUser = usersRepository.findByEmail(form.getEmail());
        if (optionalUser.isPresent()) {
            throw new InTouchException("User with email " + form.getEmail() + " already exist");
        }
        User user = userMapper.toUser(form);
        User savedUser = usersRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto signIn(SignInForm form) throws InTouchException {
        if(form.getEmail() == null) {
            throw new InTouchException("Email cannot be null");
        }
        Optional<User> optionalUser = usersRepository.findByEmail(form.getEmail());
        if(optionalUser.isEmpty()) {
            throw new InTouchException("User with email " + form.getEmail() + " not found.");
        }
        User user = optionalUser.get();
        if(!passwordEncoder.matches(form.getPassword(), user.getPasswordHash())) {
            throw new InTouchException("Wrong password");
        }
        return userMapper.toDto(user);
    }
}
