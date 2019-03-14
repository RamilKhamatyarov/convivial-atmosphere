package ru.rkhamatyarov.convivialcompetition.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Report show progress of competition that contains {@link AccountExperimentAmountCard}
 * objects and {@link MemberAchievementStatus}
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class ProgressReport {
    private final Long memberId;
    private final Long experimentAmount;
    private final List<MemberAchievementStatus> memberAchievementStatusList;

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
    public List<MemberAchievementStatus> getAchievementCard() {
        return Collections.unmodifiableList(memberAchievementStatusList);
    }

}
