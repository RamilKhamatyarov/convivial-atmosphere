package ru.rkhamatyarov.convivialatmosphere.event;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class TaskSolvedEvent implements Serializable {
    private final Long solveNumberOfAttempts;
    private final Long memberId;
    private final Boolean isCorrect;
}

