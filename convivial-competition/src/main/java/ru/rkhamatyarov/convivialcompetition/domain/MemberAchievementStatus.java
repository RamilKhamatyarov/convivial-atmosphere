package ru.rkhamatyarov.convivialcompetition.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


/**
 * Entity for Members and Achievements
 */
@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public final class MemberAchievementStatus {

    @Id
    @GeneratedValue
    @Column(name = "ACHIEVEMENT_STATUS_ID")
    private final Long achievementStatusId;

    private final Long memberId;
    private final Long achievementCardTimestamp;
    private final AchievementCard achievementCard;

    public MemberAchievementStatus() {
        this(null, null, 0L, null);
    }

    public MemberAchievementStatus(Long memberId, AchievementCard achievementCard) {
        this(null, memberId, System.currentTimeMillis(), achievementCard);
    }
}
