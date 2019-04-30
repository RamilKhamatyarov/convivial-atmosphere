package ru.rkhamatyarov.convivialcompetition.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.rkhamatyarov.convivialcompetition.domain.ProgressReport;
import ru.rkhamatyarov.convivialcompetition.service.ReportServiceImpl;

@RestController
@RequestMapping("/report")
@AllArgsConstructor
public class MemberReportController {
    private ReportServiceImpl reportService;

    @GetMapping
    public ProgressReport getReportController(@RequestParam("memberId") Long memberId) {
        return reportService.retrieveReportForMember(memberId);
    }
}
