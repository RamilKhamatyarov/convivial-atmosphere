package ru.rkhamatyarov.convivialatmosphere.contoller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import ru.rkhamatyarov.convivialatmosphere.contoller.rs.MultiplicationSuccessRs;
import ru.rkhamatyarov.convivialatmosphere.domain.Multiplication;
import ru.rkhamatyarov.convivialatmosphere.domain.MultiplicationResultTry;
import ru.rkhamatyarov.convivialatmosphere.domain.User;
import ru.rkhamatyarov.convivialatmosphere.service.MultiplicationService;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(MultiplicationCheckResultController.class)
public class MultiplicationCheckResultControllerTest {

    @MockBean
    private MultiplicationService service;

    @Autowired
    private MockMvc mockMvc;

    private JacksonTester<MultiplicationResultTry> jsonMultiplicationResultTry;
    private JacksonTester<MultiplicationSuccessRs> jsonSuccessResultResponse;

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

    void genericParametrizedTest(Boolean isMultiplySuccess) throws Exception {
        // given
        given(service.checkTry(any(MultiplicationResultTry.class))).willReturn(isMultiplySuccess);

        User user = new User();
        Multiplication multiplication = new Multiplication(13, 42);

        MultiplicationResultTry multiplicationResultTry = new MultiplicationResultTry(user, multiplication, 13*42);

        // when
        MockHttpServletResponse servletResponse = mockMvc.perform(
                post("/results").
                        contentType(APPLICATION_JSON).
                        content(jsonMultiplicationResultTry.write(multiplicationResultTry).getJson())).
                andReturn().getResponse();

        // then
        assertThat(servletResponse.getStatus()).isEqualTo(OK.value());
        assertThat(servletResponse.getContentAsString()).isEqualTo(
                jsonSuccessResultResponse.write(
                        new MultiplicationSuccessRs(isMultiplySuccess)
                ).getJson()
        );
    }

}