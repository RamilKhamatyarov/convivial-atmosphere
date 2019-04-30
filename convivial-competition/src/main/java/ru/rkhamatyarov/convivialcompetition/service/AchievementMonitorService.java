package ru.rkhamatyarov.convivialcompetition.service;

import ru.rkhamatyarov.convivialcompetition.domain.AchievementMonitor;

import java.util.List;

public interface AchievementMonitorService {
    /**
     * Retrieves the current leader board/monitor with the top score
     * members
     * @return
     *         a {@link AchievementMonitor} the member with highest experiments
     */
    List<AchievementMonitor> getCurrentAchievementMonitor();
}
