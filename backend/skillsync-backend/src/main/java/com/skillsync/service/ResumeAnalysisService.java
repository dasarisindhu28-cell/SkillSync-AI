
package com.skillsync.service;

import com.skillsync.model.ResumeAnalysis;
import com.skillsync.repository.ResumeAnalysisRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResumeAnalysisService {

    private final ResumeAnalysisRepository repository;

    public ResumeAnalysisService(ResumeAnalysisRepository repository) {
        this.repository = repository;
    }

    public ResumeAnalysis saveAnalysis(
            String filename, List<String> skills) {

        String skillsText = String.join(", ", skills);

        ResumeAnalysis analysis =
                new ResumeAnalysis(filename, skillsText);

        return repository.save(analysis);
    }

    public List<ResumeAnalysis> getAllAnalyses() {
        return repository.findAll();
    }
}
