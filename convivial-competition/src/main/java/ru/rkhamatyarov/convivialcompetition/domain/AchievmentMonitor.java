package ru.rkhamatyarov.convivialcompetition.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class AchievmentMonitor {

    private final Long memberId;
    private final Long totalExperiments;

    public AchievmentMonitor() {
        this(0L, 0L);
    }
}
