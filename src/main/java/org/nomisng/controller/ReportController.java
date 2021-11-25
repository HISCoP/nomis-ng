package org.nomisng.controller;

import lombok.RequiredArgsConstructor;
import org.nomisng.domain.dto.ReportDetailDTO;
import org.nomisng.domain.dto.ReportInfoDTO;
import org.nomisng.domain.entity.ReportInfo;
import org.nomisng.service.birt.BirtReportService;
import org.nomisng.service.birt.OutputType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/reports")
@Controller
@RequiredArgsConstructor
public class ReportController {
    private static final Logger log = LoggerFactory.getLogger(ReportController.class);

    private final BirtReportService reportService;

    @PostMapping
    public ResponseEntity<ReportInfo> save(@RequestBody ReportInfoDTO reportInfoDTO) {
        reportInfoDTO.setId(0L);
        return ResponseEntity.ok(reportService.save(reportInfoDTO));
    }

    @PostMapping(value = "/generate")
    public void generateReport(@RequestBody ReportDetailDTO data, HttpServletResponse response, HttpServletRequest request) {
        String reportFormat = data.getReportFormat().toLowerCase().trim();
        OutputType format = OutputType.from(reportFormat);

        reportService.generateReport(data, format, data.getParameters(), response, request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReportInfo> update(@PathVariable Long id, @RequestBody ReportInfoDTO reportInfoDTO) {
        return ResponseEntity.ok(reportService.update(id, reportInfoDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> delete(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.delete(id));
    }

    @GetMapping
    public ResponseEntity<List<ReportInfoDTO>> getAllReports() {
        return ResponseEntity.ok(reportService.getReports());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReportInfo> update(@PathVariable Long id) {
        return ResponseEntity.ok(reportService.getReport(id));
    }
}