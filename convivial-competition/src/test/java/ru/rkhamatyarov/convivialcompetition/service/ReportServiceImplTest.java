package ru.rkhamatyarov.convivialcompetition.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.rkhamatyarov.convivialcompetition.domain.AccountExperimentAmountCard;
import ru.rkhamatyarov.convivialcompetition.domain.AchievementCard;
import ru.rkhamatyarov.convivialcompetition.domain.MemberAchievementStatus;
import ru.rkhamatyarov.convivialcompetition.domain.ProgressReport;
import ru.rkhamatyarov.convivialcompetition.repository.AccountExperimentAmountCardRepository;
import ru.rkhamatyarov.convivialcompetition.repository.MemberAchievementStatusRepository;

import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static ru.rkhamatyarov.convivialcompetition.domain.AchievementCard.SILVER_CARD;


public class ReportServiceImplTest {
    private ReportServiceImpl reportService;

    @Mock
    private AccountExperimentAmountCardRepository scoreCardRepository;

    @Mock
    private MemberAchievementStatusRepository badgeCardRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        reportService = new ReportServiceImpl(scoreCardRepository, badgeCardRepository);
    }

    @Test
    public void newAttemptForMember() {
        // given
        Long memberId = 1L;
        Long attemptId = 1L;
        Integer totalScore = 466;

        AccountExperimentAmountCard accountExperimentAmountCard = new AccountExperimentAmountCard(memberId, attemptId);

        given(scoreCardRepository.getTotalAccountForMember(memberId))
                .willReturn(totalScore);
        given(scoreCardRepository.findByMemberIdOrderByAccountTimestampDesc(memberId))
                .willReturn(Collections.singletonList(accountExperimentAmountCard));
        given(badgeCardRepository.findByMemberIdByAchievmentCardTimestampDesc(memberId))
                .willReturn(Collections.emptyList());

        // when
        ProgressReport progressReport = reportService.newAttemptForMember(memberId, attemptId, true);

        // assert
        assertThat(progressReport.getExperimentAmount()).isEqualTo(accountExperimentAmountCard.getAccount());
        assertThat(progressReport.getMemberAchievementStatusList()).containsOnlyOnce(AchievementCard.PLATINUM_CARD);
    }

    @Test
    public void newAttemptForMemberNotCorrect() {
        // given
        Long memberId = 1L;
        Long attemptId = 1L;
        Integer totalScore = 466;

        AccountExperimentAmountCard accountExperimentAmountCard = new AccountExperimentAmountCard(memberId, attemptId);

        given(scoreCardRepository.getTotalAccountForMember(memberId))
                .willReturn(totalScore);
        given(scoreCardRepository.findByMemberIdOrderByAccountTimestampDesc(memberId))
                .willReturn(Collections.singletonList(accountExperimentAmountCard));
        given(badgeCardRepository.findByMemberIdByAchievmentCardTimestampDesc(memberId))
                .willReturn(Collections.emptyList());

        // when
        ProgressReport progressReport = reportService.newAttemptForMember(memberId, attemptId, false);

        // assert
        assertThat(progressReport.getExperimentAmount()).isEqualTo(0);
        assertThat(progressReport.getMemberAchievementStatusList()).isEmpty();
    }

    @Test
    public void retrieveReportForMember() {
        // given
        Long memberId = 1L;
        Long attemptId = 1L;
        Long totalScore = 466L;

        MemberAchievementStatus memberAchievementStatus = new MemberAchievementStatus(memberId, SILVER_CARD);

        given(scoreCardRepository.getTotalAccountForMember(memberId))
                .willReturn(Math.toIntExact(totalScore));
        given(badgeCardRepository.findByMemberIdByAchievmentCardTimestampDesc(memberId))
                .willReturn(Collections.singletonList(memberAchievementStatus));

        // when
        ProgressReport progressReport = reportService.retrieveReportForMember(memberId);

        // assert
        assertThat(progressReport.getExperimentAmount()).isEqualTo(totalScore);
        assertThat(progressReport.getAchievementCard()).containsOnly(SILVER_CARD);
    }
}