package ru.rkhamatyarov.convivialatmosphere.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public final class Multiplication {
    private final Integer leftMultiplier;
    private final Integer rightMultiplier;

    /**
     * For JSON (de)serialization
     */
    public Multiplication() {
        this(0, 0);
    }
}
