package ru.rkhamatyarov.convivialatmosphere.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class MultiplicationResultTry {

    private final User user;
    private final Multiplication multiplication;
    private final Integer multiplicationResult;

    public MultiplicationResultTry() {

        user = null;
        multiplication = null;
        multiplicationResult = -1;
    }
}
