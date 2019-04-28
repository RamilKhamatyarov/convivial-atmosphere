package ru.rkhamatyarov.convivialcompetition.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * Represents a line in our monitor, links a member to a total score/experiments
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class AchievementMonitor {

    private final Long memberId;
    private final Long totalExperiments;

    /**
     * Constructor for JSON/JPA
     */
    public AchievementMonitor() {
        this(0L, 0L);
    }
}
