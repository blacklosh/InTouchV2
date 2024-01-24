package intouch.repository;

import intouch.model.User;
import intouch.repository.base.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UsersRepository extends CrudRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
