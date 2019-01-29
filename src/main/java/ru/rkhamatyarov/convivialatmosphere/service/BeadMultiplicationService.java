package ru.rkhamatyarov.convivialatmosphere.service;

import ru.rkhamatyarov.convivialatmosphere.domain.BeadMultiplication;

public interface BeadMultiplicationService {
    /**
     * Create BeadMultiplication from two randomly generates beads
     * @return a BeadMultiplication with two random radius
     */
    BeadMultiplication createRandomBeadMultiplication();

}
