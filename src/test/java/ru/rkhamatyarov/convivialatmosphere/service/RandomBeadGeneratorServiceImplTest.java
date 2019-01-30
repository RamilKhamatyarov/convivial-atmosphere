package ru.rkhamatyarov.convivialatmosphere.service;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class RandomBeadGeneratorServiceImplTest {

    private RandomBeadGeneratorServiceImpl generatorServiceImpl;


    @Before
    public void setUp(){
        generatorServiceImpl = new RandomBeadGeneratorServiceImpl();
    }

    @Test
    public void generateRandomBeadFactorTest() {
        List<Integer> randomBeads = IntStream.range(0, 1000)
                .map(i -> generatorServiceImpl.generateRandomBeadFactor())
                .boxed().collect(Collectors.toList());

        assertThat(randomBeads).containsOnlyElementsOf(IntStream.range(11, 100)
                .boxed().collect(Collectors.toList()));
    }
}
