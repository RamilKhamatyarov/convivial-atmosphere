package ru.rkhamatyarov.convivialatmosphere.service;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rkhamatyarov.convivialatmosphere.domain.Multiplication;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;
import ru.rkhamatyarov.convivialatmosphere.domain.User;
import ru.rkhamatyarov.convivialatmosphere.event.EventDispatcher;
import ru.rkhamatyarov.convivialatmosphere.event.TaskSolvedEvent;
import ru.rkhamatyarov.convivialatmosphere.repository.MultiplicationTryRepository;
import ru.rkhamatyarov.convivialatmosphere.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.springframework.util.Assert.isTrue;
import static ru.rkhamatyarov.convivialatmosphere.util.PrettyJsonBuilder.prettyJson;

@Slf4j
@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private RandomIntegerGeneratorService generatorService;
    private UserRepository userRepository;
    private MultiplicationTryRepository multiplicationTryRepository;
    private EventDispatcher eventDispatcher;

    MultiplicationServiceImpl(
            RandomIntegerGeneratorService generatorService,
            UserRepository userRepository,
            MultiplicationTryRepository multiplicationTryRepository,
            EventDispatcher eventDispatcher
    ) {
        this.generatorService = generatorService;
        this.userRepository = userRepository;
        this.multiplicationTryRepository = multiplicationTryRepository;
        this.eventDispatcher = eventDispatcher;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        Integer leftMultiplier = generatorService.generateRandomBeadFactor();
        Integer rightMultiplier = generatorService.generateRandomBeadFactor();

        Multiplication randomMultiplication = new Multiplication(leftMultiplier, rightMultiplier);
        log.debug("Made random multiplication: {}", prettyJson(randomMultiplication));

        return randomMultiplication;
    }

    @Transactional
    @Override
    public Boolean checkTry(@NonNull final MultiplicationResultTry multiplicationResult) {

        Optional<User> user = userRepository.findByLogin(multiplicationResult.getUser().getLogin());

        Boolean isResultRight = multiplicationResult.getMultiplication().getLeftMultiplier() *
                multiplicationResult.getMultiplication().getRightMultiplier() ==
                multiplicationResult.getMultiplicationResult();

        isTrue(!multiplicationResult.isRightResult(), "You cannot send success result.");

        MultiplicationResultTry rightResult = new MultiplicationResultTry(
                user.orElse(multiplicationResult.getUser()),
                multiplicationResult.getMultiplication(),
                multiplicationResult.getMultiplicationResult(),
                isResultRight
                );

        multiplicationTryRepository.save(rightResult);

        // Publish event to the queue (topic way)
            eventDispatcher.send(
                new TaskSolvedEvent(
                        rightResult.getId(),
                        rightResult.getUser().getId(),
                        rightResult.isRightResult()
                ));

        return isResultRight;
    }

    @Override
    public List<MultiplicationResultTry> getUserMultiplicationCountOfTries(String login) {
        log.info(">>> Service layer: get user last 13 tries {}", multiplicationTryRepository.findTop13ByUserLoginOrderByIdDesc(login));
        return multiplicationTryRepository.findTop13ByUserLoginOrderByIdDesc(login);
    }
}
