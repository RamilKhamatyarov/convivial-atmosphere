package ru.rkhamatyarov.convivialatmosphere.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * Checks the result of {@link Multiplication} by {@link User}
 *
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class MultiplicationResultTry {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private final User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_OF_MULTIPLICATION")
    private final Multiplication multiplication;

    private final Integer multiplicationResult;

    private final boolean isRightResult;

    public MultiplicationResultTry() {

        user = null;
        multiplication = null;
        multiplicationResult = -1;
        isRightResult = Boolean.FALSE;
    }
}
