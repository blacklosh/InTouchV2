package intouch.services;

import intouch.dto.SignInForm;
import intouch.dto.SignUpForm;
import intouch.dto.UserDto;
import intouch.exceptions.InTouchException;

public interface AuthorizationService {
    UserDto signUp(SignUpForm form) throws InTouchException;
    UserDto signIn(SignInForm form) throws InTouchException;
}
