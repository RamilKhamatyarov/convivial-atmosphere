package ru.rkhamatyarov.convivialcompetition.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.rkhamatyarov.convivialcompetition.client.dto.MultiplicationResultTry;

/**
 * Connect Multiplication microservice via REST
 */
@Component
public class MultiplicationResultTryClientImpl implements MultiplicationResultTryClient {

    private final RestTemplate restTemplate;
    private final String multiplicationHost;

    public MultiplicationResultTryClientImpl(
            final RestTemplate restTemplate,
            @Value("${multiplicationHost}") final String multiplicationHost) {
        this.restTemplate = restTemplate;
        this.multiplicationHost = multiplicationHost;
    }

    @Override
    public MultiplicationResultTry retrieveMultiplicationResultTryId(Long tryId) {
        return restTemplate.getForObject(
                multiplicationHost + "/results/" + tryId,
                MultiplicationResultTry.class
        );
    }
}
