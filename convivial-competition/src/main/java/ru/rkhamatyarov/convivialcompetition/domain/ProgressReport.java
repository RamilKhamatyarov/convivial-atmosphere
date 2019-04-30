package ru.rkhamatyarov.convivialcompetition.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Report show progress of competition that contains {@link AccountExperimentAmountCard}
 * objects and {@link MemberAchievementStatus}
 */


@Getter
@ToString
@EqualsAndHashCode
@RequiredArgsConstructor

public class ProgressReport {
    private final Long memberId;
    private final Long experimentAmount;
    private final List<AchievementCard> memberAchievementStatusList;

    public ProgressReport() {
        this.memberId = 0L;
        this.experimentAmount = 0L;
        this.memberAchievementStatusList = new ArrayList<>();
    }

    /**
     * Factory for empty report
     * @param memberId
     * @return
     *      report of experiment progress
     */
    public static ProgressReport emptyReport(final Long memberId) {
        return new ProgressReport(memberId, 0L, Collections.emptyList());
    }

    /**
     * @return achievement status
     */
    public List<AchievementCard> getAchievementCard() {
        return Collections.unmodifiableList(memberAchievementStatusList);
    }

}
