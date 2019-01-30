package ru.rkhamatyarov.convivialatmosphere.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomBeadGeneratorServiceImpl implements RandomBeadGeneratorService{
    private final static Integer MIN_BEAD = 11;
    private final static Integer MAX_BEAD = 99;

    @Override
    public Integer generateRandomBeadFactor() {
        return new Random().nextInt((MAX_BEAD - MIN_BEAD) + 1) + MIN_BEAD;
    }
}
