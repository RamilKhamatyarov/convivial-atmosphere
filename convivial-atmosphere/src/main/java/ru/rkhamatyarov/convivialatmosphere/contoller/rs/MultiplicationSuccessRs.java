package ru.rkhamatyarov.convivialatmosphere.contoller.rs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public final class MultiplicationSuccessRs {
    private final Boolean isMultiplicationSuccess;
}
