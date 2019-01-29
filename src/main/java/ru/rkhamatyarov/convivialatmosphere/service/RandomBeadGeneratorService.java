package ru.rkhamatyarov.convivialatmosphere.service;

import java.math.BigInteger;

public interface RandomBeadGeneratorService {

    /**
     * @return return bead with randomly generated radius
     */
    BigInteger generateRandomBeadFactor();
}
