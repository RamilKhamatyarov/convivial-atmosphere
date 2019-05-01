package ru.rkhamatyarov.convivialcompetition.event;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.rkhamatyarov.convivialatmosphere.event.TaskSolvedEvent;
import ru.rkhamatyarov.convivialcompetition.domain.ProgressReport;
import ru.rkhamatyarov.convivialcompetition.service.ReportService;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.anyCollectionOf;
import static org.mockito.Mockito.verify;

public class EventHandlerTest {
    private EventHandler eventHandler;

    @Mock
    private ReportService reportService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        eventHandler = new EventHandler(reportService);
    }

    @Test
    public void handleTaskSolved() {
        // given
        Long memberId = 42L;
        Long attemptId = 66L;
        boolean correct = true;
        ProgressReport report = new ProgressReport();
        TaskSolvedEvent event = new TaskSolvedEvent(attemptId, memberId, correct);
        given(reportService.newAttemptForMember(memberId, attemptId, correct)).willReturn(report);

        // when
        eventHandler.handleTaskSolved(event);

        // verify
        verify(reportService).newAttemptForMember(memberId, attemptId, correct);

    }
}