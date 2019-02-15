package ru.rkhamatyarov.convivialatmosphere.service;

import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.rkhamatyarov.convivialatmosphere.domain.Multiplication;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;
import ru.rkhamatyarov.convivialatmosphere.domain.User;
import ru.rkhamatyarov.convivialatmosphere.repository.MultiplicationTryRepository;
import ru.rkhamatyarov.convivialatmosphere.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

public class MultiplicationServiceImplTest {
    private static final String TEST_USER_LOGIN = "testUser";

    private MultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomIntegerGeneratorService generatorService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MultiplicationTryRepository multiplicationTryRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new MultiplicationServiceImpl(generatorService, userRepository, multiplicationTryRepository);
    }

    @Test
    public void createRandomMultiplicationTest() {

        given(generatorService.generateRandomBeadFactor()).willReturn(13, 42);

        Multiplication beadMultiplication = multiplicationServiceImpl.createRandomMultiplication();

        assertThat(beadMultiplication.getLeftMultiplier()).isEqualTo(13);
        assertThat(beadMultiplication.getRightMultiplier()).isEqualTo(42);
    }

    @Test
    public void checkIsRightResultSuccessTest() {
        Multiplication multiplication = new Multiplication(13, 42);
        User user = new User(TEST_USER_LOGIN);

        MultiplicationResultTry multiplicationResultTry = new MultiplicationResultTry(user, multiplication, 13 * 42, false);
        MultiplicationResultTry verifyMultResultTry = new MultiplicationResultTry(user, multiplication, 13 * 42, true);

        given(userRepository.findByLogin(TEST_USER_LOGIN)).willReturn(Optional.empty());

        Boolean isMultilplicationSucc = multiplicationServiceImpl.checkTry(multiplicationResultTry);

        assertThat(isMultilplicationSucc).isTrue();

        verify(multiplicationTryRepository).save(verifyMultResultTry);

    }

    @Test
    public void checkIsRightResultFailTest() {
        Multiplication multiplication = new Multiplication(13, 42);
        User user = new User(TEST_USER_LOGIN);

        MultiplicationResultTry multiplicationResultTry = new MultiplicationResultTry(user, multiplication, 13 * 40, false);

        given(userRepository.findByLogin(TEST_USER_LOGIN)).willReturn(Optional.empty());

        Boolean isMultilplicationSucc = multiplicationServiceImpl.checkTry(multiplicationResultTry);

        assertThat(isMultilplicationSucc).isFalse();

        verify(multiplicationTryRepository).save(multiplicationResultTry);

    }

    @Test
    public void getUserMultiplicationCountOfTriesTest() {
        // let
        Multiplication multiplication = new Multiplication(13, 42);
        User user = new User(TEST_USER_LOGIN);

        MultiplicationResultTry multiplicationResultTry1 = new MultiplicationResultTry(user, multiplication, 12 * 42, false);
        MultiplicationResultTry multiplicationResultTry2 = new MultiplicationResultTry(user, multiplication, 14 * 42, false);

        List<MultiplicationResultTry> tryListIn = Lists.newArrayList(multiplicationResultTry1, multiplicationResultTry2);

        given(userRepository.findByLogin(TEST_USER_LOGIN)).willReturn(Optional.empty());
        given(multiplicationTryRepository.findTop13ByUserLoginOrderByIdDesc(TEST_USER_LOGIN)).willReturn(tryListIn);

        // solve
        List<MultiplicationResultTry> tryListOut = multiplicationServiceImpl.getUserMultiplicationCountOfTries(TEST_USER_LOGIN);

        // assert
        assertThat(tryListOut).isEqualTo(tryListIn);
    }
}