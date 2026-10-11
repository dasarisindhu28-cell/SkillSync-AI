
package com.skillsync.controller;

import com.skillsync.service.ResumeExtractionService;
import com.skillsync.service.ResumeAnalysisService;
import com.skillsync.service.SkillExtractionService;
import com.skillsync.model.ResumeAnalysis;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/resume")
@CrossOrigin(origins = "http://localhost:5173")
public class ResumeController {

    private final ResumeExtractionService resumeExtractionService;
    private final ResumeAnalysisService resumeAnalysisService;
    private final SkillExtractionService skillExtractionService;

    public ResumeController(
            ResumeExtractionService resumeExtractionService,
            ResumeAnalysisService resumeAnalysisService,
            SkillExtractionService skillExtractionService) {

        this.resumeExtractionService = resumeExtractionService;
        this.resumeAnalysisService = resumeAnalysisService;
        this.skillExtractionService = skillExtractionService;
    }

    @PostMapping(
        value = "/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Map<String, Object>> uploadResume(
            @RequestParam("file") MultipartFile file)
            throws IOException {
                System.out.println("Resume upload controller reached");

        String extractedText =
                resumeExtractionService.extractText(file);

        List<String> skills =
                skillExtractionService.extractSkills(extractedText);

        ResumeAnalysis savedAnalysis =
                resumeAnalysisService.saveAnalysis(
                        file.getOriginalFilename(), skills);

        return ResponseEntity.ok(Map.of(
                "message", "Resume uploaded and analysis saved successfully",
                "filename", file.getOriginalFilename(),
                "resumeText", extractedText,
                "skills", skills,
                "analysisId", savedAnalysis.getId()
        ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleInvalidFile(
            IllegalArgumentException exception) {

        return ResponseEntity.badRequest().body(
                Map.of("error", exception.getMessage()));
    }
}
