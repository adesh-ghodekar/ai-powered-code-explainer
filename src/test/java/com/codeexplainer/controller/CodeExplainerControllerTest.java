package com.codeexplainer.controller;

import com.codeexplainer.dto.CodeExplanationResponse;
import com.codeexplainer.dto.ExplainCodeRequest;
import com.codeexplainer.service.CodeExplainerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CodeExplainerControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @MockBean
    private CodeExplainerService codeExplainerService;
    
    private ExplainCodeRequest validRequest;
    private CodeExplanationResponse mockResponse;
    
    @BeforeEach
    void setUp() {
        validRequest = ExplainCodeRequest.builder()
                .code("x = [1, 2, 3]")
                .language("python")
                .title("List Example")
                .includeOptimization(false)
                .includeComplexity(false)
                .build();
        
        mockResponse = CodeExplanationResponse.builder()
                .id(1L)
                .code("x = [1, 2, 3]")
                .language("python")
                .title("List Example")
                .explanation("This Python code creates a list with three integers.")
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .build();
    }
    
    @Test
    void testExplainCodeSuccess() throws Exception {
        when(codeExplainerService.explainCode(any(ExplainCodeRequest.class)))
                .thenReturn(mockResponse);
        
        mockMvc.perform(post("/api/code/explain")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.language").value("python"))
                .andExpect(jsonPath("$.title").value("List Example"));
    }
    
    @Test
    void testExplainCodeWithEmptyCode() throws Exception {
        validRequest.setCode("");
        
        mockMvc.perform(post("/api/code/explain")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void testGetSnippetNotFound() throws Exception {
        when(codeExplainerService.getSnippetById(999L))
                .thenThrow(new IllegalArgumentException("Code snippet not found"));
        
        mockMvc.perform(get("/api/code/snippets/999"))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void testHealthCheck() throws Exception {
        mockMvc.perform(get("/api/code/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"))
                .andExpect(jsonPath("$.message").value("Code Explainer API is running"));
    }
}
