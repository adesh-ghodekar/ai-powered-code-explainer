package com.codeexplainer.service;

import com.codeexplainer.dto.CodeExplanationResponse;
import com.codeexplainer.dto.ExplainCodeRequest;
import com.codeexplainer.entity.CodeSnippet;
import com.codeexplainer.repository.CodeSnippetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CodeExplainerService {
    
    private final CodeSnippetRepository codeSnippetRepository;
    private final OpenAIService openAIService;
    
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public CodeExplanationResponse explainCode(ExplainCodeRequest request) {
        log.info("Processing code explanation request for language: {}", request.getLanguage());
        
        // Validate input
        if (request.getCode() == null || request.getCode().trim().isEmpty()) {
            throw new IllegalArgumentException("Code cannot be empty");
        }
        
        if (request.getLanguage() == null || (!request.getLanguage().equalsIgnoreCase("python") 
                && !request.getLanguage().equalsIgnoreCase("javascript"))) {
            throw new IllegalArgumentException("Language must be 'python' or 'javascript'");
        }
        
        // Get explanation from OpenAI
        String explanation = openAIService.explainCode(request.getCode(), request.getLanguage());
        
        // Build entity
        CodeSnippet snippet = CodeSnippet.builder()
                .code(request.getCode())
                .language(request.getLanguage())
                .title(request.getTitle() != null ? request.getTitle() : "Untitled")
                .explanation(explanation)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        // Add optional features
        if (request.isIncludeOptimization()) {
            try {
                String optimized = openAIService.optimizeCode(request.getCode(), request.getLanguage());
                snippet.setOptimizedCode(optimized);
                snippet.setOptimizationNotes("AI-suggested optimization");
            } catch (Exception e) {
                log.error("Failed to get optimization", e);
            }
        }
        
        if (request.isIncludeComplexity()) {
            try {
                String complexity = openAIService.getComplexityAnalysis(request.getCode(), request.getLanguage());
                snippet.setComplexity(complexity);
            } catch (Exception e) {
                log.error("Failed to get complexity analysis", e);
            }
        }
        
        // Save to database
        snippet = codeSnippetRepository.save(snippet);
        log.info("Code snippet saved with ID: {}", snippet.getId());
        
        return convertToResponse(snippet);
    }
    
    public CodeExplanationResponse getSnippetById(Long id) {
        CodeSnippet snippet = codeSnippetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Code snippet not found with ID: " + id));
        return convertToResponse(snippet);
    }
    
    public List<CodeExplanationResponse> getAllSnippets() {
        List<CodeSnippet> snippets = codeSnippetRepository.findAllByOrderByCreatedAtDesc();
        return snippets.stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }
    
    public void deleteSnippet(Long id) {
        if (!codeSnippetRepository.existsById(id)) {
            throw new IllegalArgumentException("Code snippet not found with ID: " + id);
        }
        codeSnippetRepository.deleteById(id);
        log.info("Code snippet deleted with ID: {}", id);
    }
    
    public CodeExplanationResponse updateSnippet(Long id, ExplainCodeRequest request) {
        CodeSnippet snippet = codeSnippetRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Code snippet not found with ID: " + id));
        
        snippet.setCode(request.getCode());
        snippet.setLanguage(request.getLanguage());
        snippet.setTitle(request.getTitle() != null ? request.getTitle() : snippet.getTitle());
        
        // Re-explain the code
        String explanation = openAIService.explainCode(request.getCode(), request.getLanguage());
        snippet.setExplanation(explanation);
        
        snippet = codeSnippetRepository.save(snippet);
        return convertToResponse(snippet);
    }
    
    private CodeExplanationResponse convertToResponse(CodeSnippet snippet) {
        return CodeExplanationResponse.builder()
                .id(snippet.getId())
                .code(snippet.getCode())
                .language(snippet.getLanguage())
                .title(snippet.getTitle())
                .explanation(snippet.getExplanation())
                .complexity(snippet.getComplexity())
                .optimizedCode(snippet.getOptimizedCode())
                .optimizationNotes(snippet.getOptimizationNotes())
                .tokensUsed(snippet.getTokensUsed())
                .createdAt(snippet.getCreatedAt().format(dateFormatter))
                .build();
    }
}
