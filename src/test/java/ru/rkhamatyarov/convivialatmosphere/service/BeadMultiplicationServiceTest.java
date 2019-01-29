package ru.rkhamatyarov.convivialatmosphere.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rkhamatyarov.convivialatmosphere.domain.BeadMultiplication;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BeadMultiplicationServiceTest {

    @MockBean
    private RandomBeadGeneratorService randomBeadGeneratiorService;

    @Autowired
    private BeadMultiplicationService beadMultiplicationService;

    @Test
    public void createRandomBeadMultiplicationTest() {
        BigInteger leftBead = new BigInteger(String.valueOf(13));
        BigInteger rightBead = new BigInteger(String.valueOf(42));

        given(randomBeadGeneratiorService.generateRandomBeadFactor()).willReturn(leftBead, rightBead);

        BeadMultiplication multiplication = beadMultiplicationService.createRandomBeadMultiplication();

        assertThat(multiplication.getLeftBeadFactor()).isEqualTo(leftBead);
        assertThat(multiplication.getRightBeadFactor()).isEqualTo(rightBead);
        assertThat(multiplication.getBeadMultiplicationResult()).isEqualTo(leftBead.multiply(rightBead));
    }
}