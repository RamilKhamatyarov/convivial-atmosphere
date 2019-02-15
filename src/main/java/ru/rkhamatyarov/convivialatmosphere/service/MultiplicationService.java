package ru.rkhamatyarov.convivialatmosphere.service;

import ru.rkhamatyarov.convivialatmosphere.domain.Multiplication;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;

import java.util.List;

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
    Boolean checkTry(final MultiplicationResultTry multiplicationResult);

    /**
     *
     * @param login
     * @return
     *          list of user multiplication result and result validation
     */
    List<MultiplicationResultTry> getUserMultiplicationCountOfTries(String login);

}
