package ru.rkhamatyarov.convivialcompetition.service;

import ru.rkhamatyarov.convivialcompetition.domain.ProgressReport;

/**
 * Main business logic for gathering stats
 */
public interface ReportService {

    /**
     * Progress a new attempt for given member
     * @param memberId
     * @param attemptId
     * @param correct
     * @return
     *      a {@link ProgressReport} - object for saving gathered statistics
     */
    ProgressReport newAttemptForMember(Long memberId, Long attemptId, boolean correct);

    /**
     * Return a progress report for given member id
     * @param memberId
     * @return
     *      a {@link ProgressReport} - the total statistic of member
     */
    ProgressReport retrieveReportForMember(Long memberId);
}
