package ru.rkhamatyarov.convivialcompetition.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import ru.rkhamatyarov.convivialcompetition.domain.AccountExperimentAmountCard;
import ru.rkhamatyarov.convivialcompetition.domain.AchievementMonitor;

import java.util.List;

public interface AccountExperimentAmountCardRepository extends CrudRepository<AccountExperimentAmountCard, Long> {

    /**
     * Get the total account for a given member, being the sum of the accounts of all member's AccountExperimentAmountCard
     * @param memberId member id which total account should be returned
     * @return
     *      the total account for the given member
     */
    @Query("SELECT SUM(s.account) FROM ru.rkhamatyarov.convivialcompetition.domain.AccountExperimentAmountCard s " +
            "WHERE s.memberId = :memberId GROUP BY s.memberId")
    int getTotalAccountForMember(@Param("memberId") final Long memberId);

    /**
     * Returns all the AccountExperimentAmountCard for a given member, identified by his member id.
     * @param memberId the id of the user
     * @return
     *         a list containing all the AccountExperimentAmountCard fot the given member, sorted by most recent
     */

    List<AccountExperimentAmountCard> findByMemberIdOrderByAccountTimestampDesc(final Long memberId);

    /**
     * Return all first 10 AccountExperimentAmountCard for all achievement monitor table
     * @return
     *      {@link List<AchievementMonitor>  list of objects that shows all progress of researching members}
     */
    @Query("SELECT NEW ru.rkhamatyarov.convivialcompetition.domain.ProgressReport(s.memberId, SUM(s.account)) " +
            "FROM ru.rkhamatyarov.convivialcompetition.domain.AccountExperimentAmountCard s " +
            "GROUP BY s.memberId ORDER BY SUM(s.account) DESC")
    List<AchievementMonitor> findFirst10();
}
