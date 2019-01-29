package ru.rkhamatyarov.convivialatmosphere.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.rkhamatyarov.convivialatmosphere.domain.BeadMultiplication;

import java.math.BigInteger;

public class BeadMultiplicationServiceImpl implements BeadMultiplicationService {

    private RandomBeadGeneratorService randomBeadGeneratorService;

    @Autowired
    public BeadMultiplicationServiceImpl(RandomBeadGeneratorService randomBeadGeneratorService) {
        this.randomBeadGeneratorService = randomBeadGeneratorService;
    }

    @Override
    public BeadMultiplication createRandomBeadMultiplication() {
        BigInteger leftBead = randomBeadGeneratorService.generateRandomBeadFactor();
        BigInteger rightBead = randomBeadGeneratorService.generateRandomBeadFactor();

        return new BeadMultiplication(leftBead, rightBead);
    }
}
