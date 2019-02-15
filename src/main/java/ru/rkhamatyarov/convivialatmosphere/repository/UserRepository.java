package ru.rkhamatyarov.convivialatmosphere.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rkhamatyarov.convivialatmosphere.domain.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByLogin(final String login);

}
