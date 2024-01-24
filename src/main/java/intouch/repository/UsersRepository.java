package ru.itis.intouch.repository;

import ru.itis.intouch.model.User;
import ru.itis.intouch.repository.base.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends CrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
