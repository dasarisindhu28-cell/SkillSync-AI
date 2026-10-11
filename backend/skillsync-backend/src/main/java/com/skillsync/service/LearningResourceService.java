
package com.skillsync.service;

import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class LearningResourceService {

    private final Map<String, Map<String, String>> resources =
            new LinkedHashMap<>();

    public LearningResourceService() {

        addResource(
                "REST API",
                "Spring Guides - REST Service",
                "https://spring.io/guides/gs/rest-service"
        );

        addResource(
                "Java",
                "Dev.java - Learn Java",
                "https://dev.java/learn/"
        );

        addResource(
                "Python",
                "Official Python Tutorial",
                "https://docs.python.org/3/tutorial/"
        );

        addResource(
                "React",
                "React Official Learn Guide",
                "https://react.dev/learn"
        );

        addResource(
                "HTML",
                "MDN - HTML Learning Area",
                "https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Structuring_content"
        );

        addResource(
                "CSS",
                "MDN - CSS Learning Area",
                "https://developer.mozilla.org/en-US/docs/Learn_web_development/Core/Styling_basics"
        );

        addResource(
                "JavaScript",
                "MDN - JavaScript Guide",
                "https://developer.mozilla.org/en-US/docs/Web/JavaScript/Guide"
        );

        addResource(
                "Spring Boot",
                "Spring Boot Official Documentation",
                "https://docs.spring.io/spring-boot/index.html"
        );

        addResource(
                "SQL",
                "SQLBolt - Interactive SQL Lessons",
                "https://sqlbolt.com/"
        );

        addResource(
                "MySQL",
                "MySQL Official Tutorial",
                "https://dev.mysql.com/doc/mysql-tutorial-excerpt/8.0/en/"
        );

        addResource(
                "Git",
                "Git Official Book",
                "https://git-scm.com/book/en/v2"
        );

        addResource(
                "GitHub",
                "GitHub Skills - Interactive Courses",
                "https://skills.github.com/"
        );

        addResource(
                "MongoDB",
                "MongoDB University",
                "https://learn.mongodb.com/"
        );

        addResource(
                "Machine Learning",
                "Google Machine Learning Crash Course",
                "https://developers.google.com/machine-learning/crash-course"
        );

        addResource(
                "Data Structures",
                "GeeksforGeeks - Data Structures",
                "https://www.geeksforgeeks.org/data-structures/"
        );

        addResource(
                "Problem Solving",
                "HackerRank - Problem Solving",
                "https://www.hackerrank.com/domains/algorithms"
        );
    }

    private void addResource(
            String skill, String title, String url) {

        Map<String, String> resource = new LinkedHashMap<>();
        resource.put("title", title);
        resource.put("url", url);

        resources.put(skill, resource);
    }

    public Map<String, String> getResource(String skill) {

        Map<String, String> resource = resources.get(skill);

        if (resource != null) {
            return resource;
        }

        Map<String, String> fallback = new LinkedHashMap<>();
        fallback.put("title", "Search learning resources for " + skill);
        fallback.put(
                "url",
                "https://www.google.com/search?q="
                        + skill.replace(" ", "+")
                        + "+tutorial"
        );

        return fallback;
    }
}
