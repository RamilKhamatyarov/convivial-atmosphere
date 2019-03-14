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
 * Linked entity competition account, experiment amount, user and achievement card
 */

@RequiredArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@Entity
public class AccountExperimentAmountCard {
    public static final Long DEFAULT_ACCOUNT = 10L;

    @Id
    @GeneratedValue
    @Column(name = "EXPERIMENT_AMOUNT_CARD")
    private final Long experimentAmountCardId;

    @Column(name = "MEMBER_ID")
    private final Long memberId;

    @Column(name = "EXPERIMENT_AMOUNT_ID")
    private final Long experimentAmountId;

    @Column(name = "ACCOUNT_TS")
    private final Long accountTimestamp;

    @Column(name = "ACCOUNT")
    private final Long account;

    public AccountExperimentAmountCard() {
        this(null, null, null, null, null);
    }

    public AccountExperimentAmountCard(Long memberId, Long experimentAmountId) {
        this(null, memberId, experimentAmountId, System.currentTimeMillis(), DEFAULT_ACCOUNT);
    }
}
