package intouch.services.impl;

import intouch.repository.UsersRepository;
import intouch.services.UsersService;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;

    @Override
    public void delete(UUID id) {
        usersRepository.delete(id);
    }
}
