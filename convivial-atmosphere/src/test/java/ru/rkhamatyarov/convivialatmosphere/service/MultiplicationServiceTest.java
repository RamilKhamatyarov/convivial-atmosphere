package ru.rkhamatyarov.convivialatmosphere.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import ru.rkhamatyarov.convivialatmosphere.domain.Multiplication;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;
import ru.rkhamatyarov.convivialatmosphere.domain.User;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MultiplicationServiceTest {

    @MockBean
    private RandomIntegerGeneratorService randomBeadGeneratiorService;

    @Autowired
    private MultiplicationService beadMultiplicationService;

    @Test
    public void createRandomBeadMultiplicationTest() {
        Integer leftBead = 13;
        Integer rightBead = 42;

        given(randomBeadGeneratiorService.generateRandomBeadFactor()).willReturn(leftBead, rightBead);

        Multiplication multiplication = beadMultiplicationService.createRandomMultiplication();

        assertThat(multiplication.getLeftMultiplier()).isEqualTo(leftBead);
        assertThat(multiplication.getRightMultiplier()).isEqualTo(rightBead);
    }

    @Test
    public void checkSuccessCorrectTryTest() {
        Multiplication multiplication = new Multiplication(13, 42);
        User user = new User("Jack");
        MultiplicationResultTry multiplicationResultTry = new MultiplicationResultTry(user, multiplication, 13*42, Boolean.FALSE);

        Boolean resultTry = beadMultiplicationService.checkTry(multiplicationResultTry);
        assertThat(resultTry).isTrue();
    }

    @Test
    public void checkFailCorrectTryTest() {
        Multiplication multiplication = new Multiplication(13, 42);
        User user = new User("Jack");
        MultiplicationResultTry multiplicationResultTry = new MultiplicationResultTry(user, multiplication, 1342, false);

        Boolean resultTry = beadMultiplicationService.checkTry(multiplicationResultTry);
        assertThat(resultTry).isFalse();
    }
}