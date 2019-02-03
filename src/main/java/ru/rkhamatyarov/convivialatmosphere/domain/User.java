package ru.rkhamatyarov.convivialatmosphere.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class User {

    private final String login;

    /**
     * For JSON (de)serialization
     */
    public User() {
        login = null;
    }


}
