
package com.skillsync.controller;

import com.skillsync.service.JobRecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/jobs")
@CrossOrigin(origins = "http://localhost:5173")
public class JobController {

    private final JobRecommendationService jobRecommendationService;

    public JobController(
            JobRecommendationService jobRecommendationService) {
        this.jobRecommendationService = jobRecommendationService;
    }

    @PostMapping("/recommend")
    public List<Map<String, Object>> recommendJobs(
            @RequestBody Map<String, List<String>> request) {

        List<String> skills = request.get("skills");

        if (skills == null) {
            skills = List.of();
        }

        return jobRecommendationService.recommendJobs(skills);
    }
}
