package ru.rkhamatyarov.convivialatmosphere.service;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.rkhamatyarov.convivialatmosphere.domain.BeadMultiplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

public class BeadMultiplicationServiceImplTest {

    private BeadMultiplicationServiceImpl multiplicationServiceImpl;

    @Mock
    private RandomBeadGeneratorService generatorService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        multiplicationServiceImpl = new BeadMultiplicationServiceImpl(generatorService);
    }

    @Test
    public void createRandomBeadMultiplicationTest() {

        given(generatorService.generateRandomBeadFactor()).willReturn(13, 42);

        BeadMultiplication beadMultiplication = multiplicationServiceImpl.createRandomBeadMultiplication();

        assertThat(beadMultiplication.getLeftBeadFactor()).isEqualTo(13);
        assertThat(beadMultiplication.getRightBeadFactor()).isEqualTo(42);
        assertThat(beadMultiplication.getBeadMultiplicationResult()).isEqualTo(546);
    }
}