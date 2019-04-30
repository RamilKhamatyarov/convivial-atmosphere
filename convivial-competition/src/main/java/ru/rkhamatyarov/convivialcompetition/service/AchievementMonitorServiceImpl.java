package ru.rkhamatyarov.convivialcompetition.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rkhamatyarov.convivialcompetition.domain.AchievementMonitor;
import ru.rkhamatyarov.convivialcompetition.repository.AccountExperimentAmountCardRepository;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class AchievementMonitorServiceImpl implements AchievementMonitorService {

    @Autowired
    private AccountExperimentAmountCardRepository scoreCardRepository;

    @Override
    public List<AchievementMonitor> getCurrentAchievementMonitor() {
        return scoreCardRepository.findFirst10();
    }
}
