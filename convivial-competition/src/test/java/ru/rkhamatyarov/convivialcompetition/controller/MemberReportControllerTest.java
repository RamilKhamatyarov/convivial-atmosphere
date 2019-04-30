package ru.rkhamatyarov.convivialcompetition.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.rkhamatyarov.convivialcompetition.domain.ProgressReport;
import ru.rkhamatyarov.convivialcompetition.service.ReportServiceImpl;

import java.util.Collections;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static ru.rkhamatyarov.convivialcompetition.domain.AchievementCard.PLATINUM_CARD;

@RunWith(SpringRunner.class)
@WebMvcTest(MemberReportController.class)
public class MemberReportControllerTest {
    private JacksonTester<ProgressReport> json;

    @MockBean
    private ReportServiceImpl reportService;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getReportController() throws Exception{
        // given
        ProgressReport progressReport = new ProgressReport(42L, 2000L, Collections.singletonList(PLATINUM_CARD));
        given(reportService.retrieveReportForMember(42L)).willReturn(progressReport);

        // when
        MockHttpServletResponse rs = mockMvc.perform(
                get("/report?memberId=42")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(rs.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(rs.getContentAsString()).isEqualTo(json.write(progressReport).getJson());
    }
}