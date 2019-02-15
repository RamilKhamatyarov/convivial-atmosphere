package ru.rkhamatyarov.convivialatmosphere.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rkhamatyarov.convivialatmosphere.domain.Multiplication;

public interface MultiplicationRepository extends CrudRepository<Multiplication, Long> {}
