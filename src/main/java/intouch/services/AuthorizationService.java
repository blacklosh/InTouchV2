package ru.itis.intouch.services;

import ru.itis.intouch.dto.SignInForm;
import ru.itis.intouch.dto.SignUpForm;
import ru.itis.intouch.dto.UserDto;
import ru.itis.intouch.exceptions.InTouchException;

public interface AuthorizationService {
    UserDto signUp(SignUpForm form) throws InTouchException;
    UserDto signIn(SignInForm form) throws InTouchException;
}
