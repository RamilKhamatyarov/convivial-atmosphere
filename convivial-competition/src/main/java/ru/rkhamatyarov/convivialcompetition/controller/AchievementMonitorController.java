package ru.rkhamatyarov.convivialcompetition.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.rkhamatyarov.convivialcompetition.domain.AchievementMonitor;
import ru.rkhamatyarov.convivialcompetition.service.AchievementMonitorServiceImpl;

import java.util.List;

/**
 * Achievement monitor leader board REST API controller of the service
 */
@RestController
@RequestMapping("/achievements")
@AllArgsConstructor
public class AchievementMonitorController {

    private final AchievementMonitorServiceImpl achievementMonitorService;

    @GetMapping
    public List<AchievementMonitor> getAchievementMonitor() {
        return achievementMonitorService.getCurrentAchievementMonitor();
    }

}
