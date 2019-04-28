package ru.rkhamatyarov.convivialcompetition.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.rkhamatyarov.convivialcompetition.repository.AccountExperimentAmountCardRepository;
import ru.rkhamatyarov.convivialcompetition.repository.MemberAchievementStatusRepository;


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
    }

    @Test
    public void retrieveReportForMember() {
    }
}