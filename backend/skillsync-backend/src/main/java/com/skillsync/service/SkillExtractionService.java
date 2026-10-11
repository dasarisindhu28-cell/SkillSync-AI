
package com.skillsync.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class SkillExtractionService {

    private static final List<String> KNOWN_SKILLS = List.of(
        "Java", "Python", "JavaScript", "React",
        "HTML", "CSS", "Spring Boot", "SQL",
        "MySQL", "MongoDB", "REST API", "Git",
        "GitHub", "Machine Learning",
        "Data Structures", "Problem Solving"
    );

    public List<String> extractSkills(String resumeText) {
        List<String> extractedSkills = new ArrayList<>();

        if (resumeText == null || resumeText.isBlank()) {
            return extractedSkills;
        }

        String text = resumeText.toLowerCase(Locale.ROOT);

        for (String skill : KNOWN_SKILLS) {
            if (text.contains(skill.toLowerCase(Locale.ROOT))) {
                extractedSkills.add(skill);
            }
        }

        return extractedSkills;
    }
}
