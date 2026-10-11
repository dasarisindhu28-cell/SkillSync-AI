
package com.skillsync.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class JobRecommendationService {

    private final Map<String, List<String>> jobSkills =
            new LinkedHashMap<>();

    private final LearningResourceService learningResourceService;

    public JobRecommendationService(
            LearningResourceService learningResourceService) {

        this.learningResourceService = learningResourceService;

        jobSkills.put("Java Backend Developer",
                List.of("Java", "Spring Boot", "SQL",
                        "MySQL", "REST API", "Git"));

        jobSkills.put("Frontend Developer",
                List.of("HTML", "CSS", "JavaScript",
                        "React", "Git", "GitHub"));

        jobSkills.put("Full Stack Developer",
                List.of("Java", "Spring Boot", "React",
                        "HTML", "CSS", "SQL", "Git"));

        jobSkills.put("Python Developer",
                List.of("Python", "SQL", "REST API",
                        "Git", "Problem Solving"));

        jobSkills.put("Machine Learning Engineer",
                List.of("Python", "Machine Learning",
                        "SQL", "Data Structures"));
    }

    public List<Map<String, Object>> recommendJobs(
            List<String> candidateSkills) {

        if (candidateSkills == null) {
            candidateSkills = List.of();
        }

        List<Map<String, Object>> recommendations =
                new ArrayList<>();

        for (Map.Entry<String, List<String>> job
                : jobSkills.entrySet()) {

            List<String> requiredSkills = job.getValue();
            List<String> matchedSkills = new ArrayList<>();
            List<String> missingSkills = new ArrayList<>();
            List<Map<String, String>> learningResources =
                    new ArrayList<>();

            for (String skill : requiredSkills) {

                boolean matched = candidateSkills.stream()
                        .anyMatch(candidateSkill ->
                                candidateSkill != null
                                && candidateSkill.equalsIgnoreCase(skill));

                if (matched) {
                    matchedSkills.add(skill);
                } else {
                    missingSkills.add(skill);

                    Map<String, String> resource =
                            new LinkedHashMap<>(
                                    learningResourceService.getResource(skill));

                    resource.put("skill", skill);
                    learningResources.add(resource);
                }
            }

            int matchScore = Math.round(
                    matchedSkills.size() * 100.0f
                            / requiredSkills.size());

            Map<String, Object> result = new LinkedHashMap<>();

            result.put("jobTitle", job.getKey());
            result.put("matchScore", matchScore);
            result.put("matchedSkills", matchedSkills.size());
            result.put("totalRequiredSkills", requiredSkills.size());
            result.put("matchedSkillNames", matchedSkills);
            result.put("missingSkills", missingSkills);
            result.put("learningResources", learningResources);

            recommendations.add(result);
        }

        recommendations.sort((first, second) ->
                Integer.compare(
                        (Integer) second.get("matchScore"),
                        (Integer) first.get("matchScore")));

        return recommendations;
    }
}
