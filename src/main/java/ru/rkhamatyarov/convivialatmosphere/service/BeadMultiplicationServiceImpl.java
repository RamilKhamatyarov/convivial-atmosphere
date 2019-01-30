package ru.rkhamatyarov.convivialatmosphere.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.rkhamatyarov.convivialatmosphere.domain.BeadMultiplication;

@Service
public class BeadMultiplicationServiceImpl implements BeadMultiplicationService {

    private RandomBeadGeneratorService randomBeadGeneratorService;

    @Autowired
    public BeadMultiplicationServiceImpl(RandomBeadGeneratorService randomBeadGeneratorService) {
        this.randomBeadGeneratorService = randomBeadGeneratorService;
    }

    @Override
    public BeadMultiplication createRandomBeadMultiplication() {
        Integer leftBead = randomBeadGeneratorService.generateRandomBeadFactor();
        Integer rightBead = randomBeadGeneratorService.generateRandomBeadFactor();

        return new BeadMultiplication(leftBead, rightBead);
    }
}
