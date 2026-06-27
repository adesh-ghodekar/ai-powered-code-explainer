package com.codeexplainer.controller;

import com.codeexplainer.dto.CodeExplanationResponse;
import com.codeexplainer.dto.ExplainCodeRequest;
import com.codeexplainer.service.CodeExplainerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/code")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
public class CodeExplainerController {
    
    private final CodeExplainerService codeExplainerService;
    
    @PostMapping("/explain")
    public ResponseEntity<CodeExplanationResponse> explainCode(@RequestBody ExplainCodeRequest request) {
        try {
            log.info("Received code explanation request");
            CodeExplanationResponse response = codeExplainerService.explainCode(request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Validation error", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error explaining code", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/snippets")
    public ResponseEntity<List<CodeExplanationResponse>> getAllSnippets() {
        try {
            List<CodeExplanationResponse> snippets = codeExplainerService.getAllSnippets();
            return ResponseEntity.ok(snippets);
        } catch (Exception e) {
            log.error("Error fetching snippets", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/snippets/{id}")
    public ResponseEntity<CodeExplanationResponse> getSnippetById(@PathVariable Long id) {
        try {
            CodeExplanationResponse snippet = codeExplainerService.getSnippetById(id);
            return ResponseEntity.ok(snippet);
        } catch (IllegalArgumentException e) {
            log.error("Snippet not found", e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error fetching snippet", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DeleteMapping("/snippets/{id}")
    public ResponseEntity<Map<String, String>> deleteSnippet(@PathVariable Long id) {
        try {
            codeExplainerService.deleteSnippet(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Snippet deleted successfully");
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Snippet not found", e);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error deleting snippet", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @PutMapping("/snippets/{id}")
    public ResponseEntity<CodeExplanationResponse> updateSnippet(
            @PathVariable Long id,
            @RequestBody ExplainCodeRequest request) {
        try {
            CodeExplanationResponse response = codeExplainerService.updateSnippet(id, request);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            log.error("Validation error", e);
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            log.error("Error updating snippet", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("message", "Code Explainer API is running");
        return ResponseEntity.ok(response);
    }
}
