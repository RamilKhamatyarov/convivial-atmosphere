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
import ru.rkhamatyarov.convivialcompetition.domain.AchievementMonitor;
import ru.rkhamatyarov.convivialcompetition.domain.ProgressReport;
import ru.rkhamatyarov.convivialcompetition.service.AchievementMonitorServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(AchievementMonitorController.class)
public class AchievementMonitorControllerTest {
    private JacksonTester<List<AchievementMonitor>> json;

    @MockBean
    private AchievementMonitorServiceImpl service;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        JacksonTester.initFields(this, new ObjectMapper());
    }

    @Test
    public void getAchievementMonitor() throws Exception{
        // given
        AchievementMonitor achievementMonitor1 = new AchievementMonitor(42L, 466L);
        AchievementMonitor achievementMonitor2 = new AchievementMonitor(69L, 1000L);
        List<AchievementMonitor> achievementMonitorList = new ArrayList<>();
        Collections.addAll(achievementMonitorList, achievementMonitor1, achievementMonitor2);
        given(service.getCurrentAchievementMonitor()).willReturn(achievementMonitorList);

        // when
        MockHttpServletResponse rs = mockMvc.perform(
                get("/achievements")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // assert
        assertThat(rs.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(rs.getContentAsString()).isEqualTo(json.write(achievementMonitorList).getJson());

    }
}