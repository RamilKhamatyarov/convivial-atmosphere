package ru.rkhamatyarov.convivialatmosphere.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import ru.rkhamatyarov.convivialatmosphere.domain.Multiplication;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;
import ru.rkhamatyarov.convivialatmosphere.domain.User;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class MultiplicationServiceImplTest {

    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomBeadGeneratorService generatorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(generatorService);
    }

    @Test
    public void createRandomBeadMultiplicationTest() {

        given(generatorService.generateRandomBeadFactor()).willReturn(13, 42);

        Multiplication beadMultiplication = multiplicationServiceImpl.createRandomMultiplication();

        assertThat(beadMultiplication.getLeftMultiplier()).isEqualTo(13);
        assertThat(beadMultiplication.getRightMultiplier()).isEqualTo(42);
    }

    @Test
    public void checkIsRightResultSuccessTest() {
        Multiplication multiplication = new Multiplication(13, 42);
        User user = new User("testUser");

        MultiplicationResultTry multiplicationResultTry = new MultiplicationResultTry(user, multiplication, 13*42, false);

        Boolean isMultilplicationSucc = multiplicationServiceImpl.checkTry(multiplicationResultTry);

        assertThat(isMultilplicationSucc).isTrue();

    }

    @Test
    public void checkIsRightResultFailTest() {
        Multiplication multiplication = new Multiplication(13, 42);
        User user = new User("testUser");

        MultiplicationResultTry multiplicationResultTry = new MultiplicationResultTry(user, multiplication, 13*40, false);

        Boolean isMultilplicationSucc = multiplicationServiceImpl.checkTry(multiplicationResultTry);

        assertThat(isMultilplicationSucc).isFalse();

    }
}