package ru.rkhamatyarov.convivialatmosphere.service;

import ru.rkhamatyarov.convivialatmosphere.domain.Multiplication;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;

public interface MultiplicationService {
    /**
     * Create Multiplication from two randomly generates beads
     * @return a Multiplication with two random radius
     */
    Multiplication createRandomMultiplication();

    /**
     *
     * @param multiplicationResult
     * @return true if the attempt matches the result of the multiplication
     */
    boolean checkTry(final MultiplicationResultTry multiplicationResult);

}
