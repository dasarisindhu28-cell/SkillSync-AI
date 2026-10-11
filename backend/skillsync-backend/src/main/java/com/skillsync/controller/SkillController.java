
package com.skillsync.controller;

import com.skillsync.service.SkillExtractionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/skills")
@CrossOrigin(origins = "http://localhost:5173")
public class SkillController {

    private final SkillExtractionService skillExtractionService;

    public SkillController(SkillExtractionService skillExtractionService) {
        this.skillExtractionService = skillExtractionService;
    }

    @PostMapping("/extract")
    public Map<String, Object> extractSkills(
            @RequestBody Map<String, String> request) {

        String resumeText = request.get("resumeText");
        List<String> skills =
                skillExtractionService.extractSkills(resumeText);

        return Map.of(
            "skills", skills,
            "count", skills.size()
        );
    }
}
