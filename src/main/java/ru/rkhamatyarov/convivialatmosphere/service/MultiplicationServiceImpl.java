package ru.rkhamatyarov.convivialatmosphere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rkhamatyarov.convivialatmosphere.domain.Multiplication;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;

import static org.springframework.util.Assert.isTrue;

@Service
public class MultiplicationServiceImpl implements MultiplicationService {

    private RandomBeadGeneratorService randomBeadGeneratorService;

    @Autowired
    public MultiplicationServiceImpl(RandomBeadGeneratorService randomBeadGeneratorService) {
        this.randomBeadGeneratorService = randomBeadGeneratorService;
    }

    @Override
    public Multiplication createRandomMultiplication() {
        Integer leftMultiplier = randomBeadGeneratorService.generateRandomBeadFactor();
        Integer rightMultiplier = randomBeadGeneratorService.generateRandomBeadFactor();

        return new Multiplication(leftMultiplier, rightMultiplier);
    }

    @Override
    public Boolean checkTry(final MultiplicationResultTry multiplicationResult) {
        Boolean isResultRight = multiplicationResult.getMultiplication().getLeftMultiplier() *
                multiplicationResult.getMultiplication().getRightMultiplier() ==
                multiplicationResult.getMultiplicationResult();

        isTrue(!multiplicationResult.isRightResult(), "You cannot send success result.");

        MultiplicationResultTry rightResult = new MultiplicationResultTry(
                multiplicationResult.getUser(),
                multiplicationResult.getMultiplication(),
                multiplicationResult.getMultiplicationResult(),
                isResultRight
                );

        return isResultRight;
    }
}
