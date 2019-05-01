package ru.rkhamatyarov.convivialcompetition.event;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;
import org.springframework.stereotype.Component;
import ru.rkhamatyarov.convivialatmosphere.event.TaskSolvedEvent;
import ru.rkhamatyarov.convivialcompetition.service.ReportService;

@Slf4j
@Component
@AllArgsConstructor
public class EventHandler {
    private ReportService reportService;

    void handleTaskSolved(final TaskSolvedEvent event) {
        log.info("Task solved event received: {}",  event.getSolveNumberOfAttempts());
        try{
            reportService.newAttemptForMember(event.getMemberId(),
                    event.getSolveNumberOfAttempts(),
                    event.getIsCorrect());
        } catch (final Exception exc) {
            log.error("Error when trying process task solved event", exc);
            // Avoid the event to be re-queued
            throw new AmqpRejectAndDontRequeueException(exc);
        }
    }
}
