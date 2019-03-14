package ru.rkhamatyarov.convivialatmosphere.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class Multiplication {

    @Id
    @GeneratedValue
    @Column (name="ID_OF_MULTIPLICATION")
    private Long id;

    private final Integer leftMultiplier;
    private final Integer rightMultiplier;

    /**
     * For JSON (de)serialization
     */
    public Multiplication() {
        this(0, 0);
    }
}
