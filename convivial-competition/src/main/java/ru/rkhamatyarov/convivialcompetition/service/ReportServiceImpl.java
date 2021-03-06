package ru.rkhamatyarov.convivialcompetition.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rkhamatyarov.convivialcompetition.client.MultiplicationResultTryClientImpl;
import ru.rkhamatyarov.convivialcompetition.client.dto.MultiplicationResultTry;
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

import static ru.rkhamatyarov.convivialcompetition.service.constants.GameScoreTrescholds.*;

@Service
@Slf4j
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    @Autowired
    private AccountExperimentAmountCardRepository scoreCardRepository;

    @Autowired
    private MemberAchievementStatusRepository badgeCardRepository;

    @Autowired
    private MultiplicationResultTryClientImpl multiplicationResultTryClient;

    @Override
    public ProgressReport newAttemptForMember(Long memberId, Long attemptId, boolean correct) {

        if (correct) {
            AccountExperimentAmountCard accountExperimentAmountCard = new AccountExperimentAmountCard(memberId, attemptId);
            scoreCardRepository.save(accountExperimentAmountCard);

            log.info("Member id is {} scored {} points for achievement id {}.", memberId, accountExperimentAmountCard.getAccount(), attemptId);

            List<MemberAchievementStatus> memberAchievementStatusList = processForAchievementStatusList(memberId, attemptId);

            return new ProgressReport(memberId,
                    accountExperimentAmountCard.getAccount(),
                    memberAchievementStatusList.stream()
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


        checkAndGiveMemberStatusOnScore(memberAchievementStatusRanked, AchievementCard.BRONZE_CARD, totalScore, BRONZE_TRESHOLD, memberId)
                .ifPresent(memberAchievementStatusRanked::add);

        checkAndGiveMemberStatusOnScore(memberAchievementStatusRanked, AchievementCard.SILVER_CARD, totalScore, SILVER_TRESHOLD, memberId)
                .ifPresent(memberAchievementStatusRanked::add);

        checkAndGiveMemberStatusOnScore(memberAchievementStatusList, AchievementCard.GOLD_CARD, totalScore, GOLD_TRESHOLD, memberId)
                .ifPresent(memberAchievementStatusRanked::add);

        // First won achievement status
        if (memberAchievementStatusRanked.size() == 1 &&
            !containsMemberAchievementStatus(memberAchievementStatusRanked, AchievementCard.PLATINUM_CARD)) {
            MemberAchievementStatus platinumMember = giveAchievementStatusToMember(AchievementCard.PLATINUM_CARD, memberId);
            memberAchievementStatusRanked.add(platinumMember);
        }

        // Random won achievement status
        MultiplicationResultTry  resultAttempt = multiplicationResultTryClient.retrieveMultiplicationResultTryId(attemptId);
        if (!containsMemberAchievementStatus(memberAchievementStatusRanked, AchievementCard.RANDOM_CARD)
                && (RANDOM_NUMBER == resultAttempt.getLeftMultiplier()
                || RANDOM_NUMBER == resultAttempt.getRightMultiplier())) {

            MemberAchievementStatus memberAchievementStatus = giveAchievementStatusToMember(AchievementCard.RANDOM_CARD, memberId);
            memberAchievementStatusRanked.add(memberAchievementStatus);
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
        log.debug("memberAchievementStatusList {} contains achievementCard {}: {}",
                memberAchievementStatusList,
                achievementCard,
                memberAchievementStatusList.stream().anyMatch(status -> status.getAchievementCard().equals(achievementCard)));

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
