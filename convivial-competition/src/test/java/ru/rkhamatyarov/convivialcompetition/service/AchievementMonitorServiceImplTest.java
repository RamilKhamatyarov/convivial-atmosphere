package ru.rkhamatyarov.convivialcompetition.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.rkhamatyarov.convivialcompetition.domain.AchievementMonitor;
import ru.rkhamatyarov.convivialcompetition.repository.AccountExperimentAmountCardRepository;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class AchievementMonitorServiceImplTest {

    @Mock
    private AccountExperimentAmountCardRepository scoreCardRepository;

    private AchievementMonitorServiceImpl leaderBoardService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        leaderBoardService = new AchievementMonitorServiceImpl(scoreCardRepository);
    }

    @Test
    public void getCurrentAchievementMonitor() {
        //given
        AchievementMonitor achievementMonitor = new AchievementMonitor();
        given(scoreCardRepository.findFirst10())
                .willReturn(Collections.singletonList(achievementMonitor));
        //when
        List<AchievementMonitor> achievementMonitorList = leaderBoardService.getCurrentAchievementMonitor();

        //assert
        assertThat(achievementMonitorList).containsAll(Collections.singletonList(achievementMonitor));
    }
}