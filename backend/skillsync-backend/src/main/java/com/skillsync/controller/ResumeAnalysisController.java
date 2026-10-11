
package com.skillsync.controller;

import com.skillsync.model.ResumeAnalysis;
import com.skillsync.service.ResumeAnalysisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/analysis")
@CrossOrigin(origins = "http://localhost:5173")
public class ResumeAnalysisController {

    private final ResumeAnalysisService service;

    public ResumeAnalysisController(ResumeAnalysisService service) {
        this.service = service;
    }

    @GetMapping("/history")
    public List<ResumeAnalysis> getHistory() {
        return service.getAllAnalyses();
    }
}
