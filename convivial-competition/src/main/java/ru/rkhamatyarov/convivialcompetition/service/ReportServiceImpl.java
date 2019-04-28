package ru.rkhamatyarov.convivialcompetition.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rkhamatyarov.convivialcompetition.domain.AccountExperimentAmountCard;
import ru.rkhamatyarov.convivialcompetition.domain.AchievementCard;
import ru.rkhamatyarov.convivialcompetition.domain.MemberAchievementStatus;
import ru.rkhamatyarov.convivialcompetition.domain.ProgressReport;
import ru.rkhamatyarov.convivialcompetition.repository.AccountExperimentAmountCardRepository;
import ru.rkhamatyarov.convivialcompetition.repository.MemberAchievementStatusRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.rkhamatyarov.convivialcompetition.service.GameScoreTrescholds.*;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportServiceImpl implements ReportService {

    private AccountExperimentAmountCardRepository scoreCardRepository;
    private MemberAchievementStatusRepository badgeCardRepository;

    @Override
    public ProgressReport newAttemptForMember(Long memberId, Long attemptId, boolean correct) {

        if (correct) {
            AccountExperimentAmountCard accountExperimentAmountCard = new AccountExperimentAmountCard(memberId, attemptId);
            scoreCardRepository.save(accountExperimentAmountCard);

            log.info("Member id is {} scored {} points for achievement id {}.", memberId, accountExperimentAmountCard.getAccount(), attemptId);

            List<MemberAchievementStatus> memberAchievementStatuseList = processForAchievementStatusList(memberId, attemptId);

            return new ProgressReport(memberId,
                    accountExperimentAmountCard.getAccount(),
                    memberAchievementStatuseList.stream()
                            .map(MemberAchievementStatus::getAchievementCard)
                            .collect(Collectors.toList()));
        }

        return ProgressReport.emptyReport(memberId);
    }

    /**
     * Checks total score and the different score cards obtained
     * to give new MemberAchievementStatus in case their conditions are met
     * @param memberId
     * @param attemptId
     * @return
     */
    private List<MemberAchievementStatus> processForAchievementStatusList(Long memberId, Long attemptId) {
        List<MemberAchievementStatus> memberAchievementStatusRanked = new ArrayList<>();

        int totalScore = scoreCardRepository.getTotalAccountForMember(memberId);

        log.info("New score for member {} is {}.", memberId, totalScore);

        List<AccountExperimentAmountCard> accountExperimentAmountCardList = scoreCardRepository.findByMemberIdOrderByAccountTimestampDesc(memberId);
        List<MemberAchievementStatus> memberAchievementStatusList = badgeCardRepository.findByMemberIdByAchievmentCardTimestampDesc(memberId);


        checkAndGiveMemberStatusOnScore(memberAchievementStatusList, AchievementCard.BRONZE_CARD, totalScore, BRONZE_TRESHOLD, memberId)
                .ifPresent(memberAchievementStatusRanked::add);

        checkAndGiveMemberStatusOnScore(memberAchievementStatusList, AchievementCard.SILVER_CARD, totalScore, SILVER_TRESHOLD, memberId)
                .ifPresent(memberAchievementStatusRanked::add);

        checkAndGiveMemberStatusOnScore(memberAchievementStatusList, AchievementCard.GOLD_CARD, totalScore, GOLD_TRESHOLD, memberId)
                .ifPresent(memberAchievementStatusRanked::add);

        if (memberAchievementStatusList.size() == 1 &&
            !containsMemberAchievementStatus(memberAchievementStatusList, AchievementCard.PLATINUM_CARD)) {
            MemberAchievementStatus platinumMember = giveAchievementStatusToMember(AchievementCard.PLATINUM_CARD, memberId);
            memberAchievementStatusRanked.add(platinumMember);
        }
        return memberAchievementStatusRanked;
    }

    private MemberAchievementStatus giveAchievementStatusToMember(AchievementCard platinumCard, Long memberId) {
        MemberAchievementStatus memberAchievementStatus = new MemberAchievementStatus(memberId, platinumCard);
        badgeCardRepository.save(memberAchievementStatus);

        log.debug("Member with id {} take new achievement {} on this chart.", memberId, platinumCard);
        return memberAchievementStatus;
    }

    private Boolean containsMemberAchievementStatus(List<MemberAchievementStatus> memberAchievementStatusList, AchievementCard achievementCard) {
        return memberAchievementStatusList.stream().anyMatch(status -> status.getAchievementCard().equals(achievementCard));
    }

    /**
     * Checks the passed member on the list is consist on the status by added score threshold
     * @param memberAchievementStatusList
     * @param achievementCard
     * @param totalScore
     * @param scoreThreshold
     * @param memberId
     * @return
     */
    private Optional<MemberAchievementStatus> checkAndGiveMemberStatusOnScore(
            final List<MemberAchievementStatus> memberAchievementStatusList,
            final AchievementCard achievementCard,
            final Integer totalScore,
            final Integer scoreThreshold,
            final Long memberId) {

        if (totalScore >= scoreThreshold && !containsMemberAchievementStatus(memberAchievementStatusList, achievementCard)) {
            return Optional.of(giveAchievementStatusToMember(achievementCard, memberId));
        }

        return Optional.empty();
    }

    @Override
    public ProgressReport retrieveReportForMember(Long memberId) {
        Long score = Long.valueOf(scoreCardRepository.getTotalAccountForMember(memberId));

        List<MemberAchievementStatus> memberAchievementStatusList = badgeCardRepository.findByMemberIdByAchievmentCardTimestampDesc(memberId);

        return new ProgressReport(memberId, score, memberAchievementStatusList.stream()
                .map(MemberAchievementStatus::getAchievementCard)
                .collect(Collectors.toList()));
    }
}
