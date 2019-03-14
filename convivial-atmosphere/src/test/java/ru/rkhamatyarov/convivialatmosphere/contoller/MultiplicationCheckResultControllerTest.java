package ru.rkhamatyarov.convivialatmosphere.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.rkhamatyarov.convivialatmosphere.domain.Multiplication;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;
import ru.rkhamatyarov.convivialatmosphere.domain.User;
import ru.rkhamatyarov.convivialatmosphere.service.MultiplicationService;

import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationCheckResultController.class)
public class MultiplicationCheckResultControllerTest {
    private static final String TEST_USER_LOGIN = "testUser";
    @MockBean
    private MultiplicationService service;

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<MultiplicationResultTry> jsonMultiplicationResultTry;
    private JacksonTester<List<MultiplicationResultTry>> jsonMultiplicationResultTryList;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void postResultReturnSuccess() throws Exception {
        genericParametrizedTest(true);
    }

    @Test
    public void postResultReturnFail() throws Exception {
        genericParametrizedTest(false);
    }

    public void genericParametrizedTest(final Boolean isMultiplySuccess) throws Exception {
        // let
        given(service.checkTry(any(MultiplicationResultTry.class))).willReturn(isMultiplySuccess);

        User user = new User();
        Multiplication multiplication = new Multiplication(13, 42);

        MultiplicationResultTry multiplicationResultTry = new MultiplicationResultTry(user, multiplication, 13*42, isMultiplySuccess);

        // solve
        MockHttpServletResponse servletResponse = mockMvc.perform(
                post("/results").
                        contentType(APPLICATION_JSON).
                        content(jsonMultiplicationResultTry.write(multiplicationResultTry).getJson())
        ).andReturn().getResponse();

        // assert
        assertThat(servletResponse.getStatus()).isEqualTo(OK.value());
        assertThat(servletResponse.getContentAsString()).isEqualTo(
                jsonMultiplicationResultTry.write(
                        new MultiplicationResultTry(
                                multiplicationResultTry.getUser(),
                                multiplicationResultTry.getMultiplication(),
                                multiplicationResultTry.getMultiplicationResult(),
                                isMultiplySuccess
                        )
                ).getJson()
        );
    }

    @Test
    public void getMultiplicationTriesTest() throws Exception {
        // let
        User user = new User(TEST_USER_LOGIN);
        Multiplication multiplication = new Multiplication(13, 42);

        MultiplicationResultTry multiplicationResultTry1 = new MultiplicationResultTry(user, multiplication, 13 * 42, true);
        MultiplicationResultTry multiplicationResultTry2 = new MultiplicationResultTry(user, multiplication, 14 * 42, false);
        MultiplicationResultTry multiplicationResultTry3 = new MultiplicationResultTry(user, multiplication, 15 * 42, false);

        List<MultiplicationResultTry> multTryList = Lists.newArrayList(multiplicationResultTry1, multiplicationResultTry2, multiplicationResultTry3);

        given(service.getUserMultiplicationCountOfTries(TEST_USER_LOGIN)).willReturn(multTryList);

        // solve
        MockHttpServletResponse servletRs = mockMvc.perform(
                get("/results").
                        param("login", TEST_USER_LOGIN)
        ).andReturn().getResponse();

        // assert
        assertThat(servletRs.getStatus()).isEqualTo(OK.value());
        assertThat(servletRs.getContentAsString()).isEqualTo(
                jsonMultiplicationResultTryList.write(
                        multTryList
                ).getJson()
        );

    }
}