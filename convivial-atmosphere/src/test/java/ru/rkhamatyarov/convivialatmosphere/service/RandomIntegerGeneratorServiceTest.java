package ru.rkhamatyarov.convivialatmosphere.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RandomIntegerGeneratorServiceTest {

    @Autowired
    private RandomIntegerGeneratorService generatorService;

    @Test
    public void generateRandomBeadFactorTest() throws Exception{
        List<Integer> randomBeads = IntStream.range(0, 1000)
                .map(i -> generatorService.generateRandomBeadFactor().intValue())
                .boxed().collect(Collectors.toList());

        assertThat(randomBeads).containsOnlyElementsOf(IntStream.range(11, 100)
                .boxed().collect(Collectors.toList()));
    }
}