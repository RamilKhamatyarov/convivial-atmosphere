package ru.rkhamatyarov.convivialatmosphere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rkhamatyarov.convivialatmosphere.domain.Multiplication;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;

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
    public boolean checkTry(MultiplicationResultTry multiplicationResult) {
        return multiplicationResult.getMultiplication().getLeftMultiplier() *
                multiplicationResult.getMultiplication().getRightMultiplier() ==
                multiplicationResult.getMultiplicationResult();
    }
}
