package ru.rkhamatyarov.convivialatmosphere.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;

import java.util.List;

public interface    MultiplicationTryRepository extends CrudRepository<MultiplicationResultTry, Long> {

    /**
     *
     * @param login
     * @return
     *          the list of {@link ru.rkhamatyarov.convivialatmosphere.domain.User}
     *          {@link ru.rkhamatyarov.convivialatmosphere.domain.Multiplication} tries
     */
    List<MultiplicationResultTry> findTop13ByUserLoginOrderByIdDesc(String login);


}
