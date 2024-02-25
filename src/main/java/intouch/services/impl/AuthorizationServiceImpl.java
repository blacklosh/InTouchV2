package intouch.services.impl;

import intouch.dto.SignInForm;
import intouch.dto.SignUpForm;
import intouch.dto.UserDto;
import intouch.exceptions.InTouchException;
import intouch.mapper.UserMapper;
import intouch.model.User;
import intouch.repository.UsersRepository;
import intouch.services.AuthorizationService;
import intouch.services.PasswordEncoder;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {
    private final UsersRepository usersRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto signUp(SignUpForm form) throws InTouchException {
        if (form.getEmail() == null || form.getEmail().isEmpty()) {
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
        if (form.getEmail() == null) {
            throw new InTouchException("Email cannot be null");
        }
        Optional<User> optionalUser = usersRepository.findByEmail(form.getEmail());
        if (optionalUser.isEmpty()) {
            throw new InTouchException("User with email " + form.getEmail() + " not found.");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(form.getPassword(), user.getPasswordHash())) {
            throw new InTouchException("Wrong password");
        }
        return userMapper.toDto(user);
    }
}