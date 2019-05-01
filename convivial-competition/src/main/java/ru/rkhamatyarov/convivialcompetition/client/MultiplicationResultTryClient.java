package ru.rkhamatyarov.convivialcompetition.client;

import ru.rkhamatyarov.convivialcompetition.client.dto.MultiplicationResultTry;

public interface MultiplicationResultTryClient {
    MultiplicationResultTry retrieveMultiplicationResultTryId(final Long tryId);
}
