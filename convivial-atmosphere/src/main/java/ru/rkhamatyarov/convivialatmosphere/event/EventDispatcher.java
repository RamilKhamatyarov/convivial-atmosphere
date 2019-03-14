package ru.rkhamatyarov.convivialatmosphere.event;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventDispatcher {

    private RabbitTemplate rabbitTemplate;

    private String taskExchange;
    private String taskSolvedRoutingKey;

    @Autowired
    public EventDispatcher(final RabbitTemplate rabbitTemplate,
                           @Value("${task.exchange}") final String taskExchange,
                           @Value("${task.solved.key}") final String taskSolvedRoutingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.taskExchange = taskExchange;
        this.taskSolvedRoutingKey = taskSolvedRoutingKey;
    }

    public void send(final TaskSolvedEvent taskSolvedEvent) {
        rabbitTemplate.convertAndSend(taskExchange, taskSolvedRoutingKey, taskSolvedEvent);
    }
}
