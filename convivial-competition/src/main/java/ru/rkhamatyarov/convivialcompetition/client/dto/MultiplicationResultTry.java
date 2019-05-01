package ru.rkhamatyarov.convivialcompetition.client.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.rkhamatyarov.convivialcompetition.client.MultiplicationResultTryDeserializer;

/**
 * Identifies the attempt from a user to solve a multiplication
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@JsonDeserialize(using=MultiplicationResultTryDeserializer.class)
public final class MultiplicationResultTry {
    private final String login;

    private final Integer leftMultiplier;
    private final Integer rightMultiplier;

    private final Integer multiplicationResult;

    private final boolean isRightResult;

    // Empty constructor for JSON/JPA

    public MultiplicationResultTry() {
        login = null;

        leftMultiplier = -1;
        rightMultiplier = -1;

        multiplicationResult = -1;

        isRightResult = false;
    }
}
