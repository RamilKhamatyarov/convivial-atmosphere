package ru.rkhamatyarov.convivialcompetition.repository;

import org.springframework.data.repository.CrudRepository;
import ru.rkhamatyarov.convivialcompetition.domain.MemberAchievementStatus;

import java.util.List;

public interface MemberAchievementStatusRepository extends CrudRepository<MemberAchievementStatus, Long> {

    /**
     * Returns all MemberAchievementStatus for a given member
     * @param memberId the Id of the user to look for MemberAchievementStatus
     * @return the list of MemberAchievementStatus, sorted by most recent
     */
    List<MemberAchievementStatus> findByMemberIdByAchievmentCardTimestampDesc(final Long memberId);
}
