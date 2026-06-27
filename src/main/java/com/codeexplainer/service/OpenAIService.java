package com.codeexplainer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class OpenAIService {
    
    @Value("${openai.api.key:}")
    private String apiKey;
    
    @Value("${openai.api.model:gpt-3.5-turbo}")
    private String model;
    
    @Value("${openai.api.url:https://api.openai.com/v1/chat/completions}")
    private String apiUrl;
    
    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public String explainCode(String code, String language) {
        if (apiKey == null || apiKey.isEmpty()) {
            log.warn("OpenAI API key not configured. Using default explanation.");
            return getDefaultExplanation(code, language);
        }
        
        try {
            String prompt = buildExplanationPrompt(code, language);
            return callOpenAI(prompt);
        } catch (IOException e) {
            log.error("Error calling OpenAI API", e);
            return getDefaultExplanation(code, language);
        }
    }
    
    public String optimizeCode(String code, String language) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "Optimization unavailable - API key not configured";
        }
        
        try {
            String prompt = buildOptimizationPrompt(code, language);
            return callOpenAI(prompt);
        } catch (IOException e) {
            log.error("Error calling OpenAI API for optimization", e);
            return "Optimization failed";
        }
    }
    
    public String getComplexityAnalysis(String code, String language) {
        if (apiKey == null || apiKey.isEmpty()) {
            return "Complexity analysis unavailable - API key not configured";
        }
        
        try {
            String prompt = buildComplexityPrompt(code, language);
            return callOpenAI(prompt);
        } catch (IOException e) {
            log.error("Error calling OpenAI API for complexity", e);
            return "Complexity analysis failed";
        }
    }
    
    private String callOpenAI(String prompt) throws IOException {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", model);
        requestBody.put("temperature", 0.7);
        requestBody.put("max_tokens", 800);
        
        // Build messages
        Map<String, String> message = new HashMap<>();
        message.put("role", "user");
        message.put("content", prompt);
        requestBody.put("messages", new Object[]{message});
        
        String jsonBody = objectMapper.writeValueAsString(requestBody);
        
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
        
        Request request = new Request.Builder()
                .url(apiUrl)
                .addHeader("Authorization", "Bearer " + apiKey)
                .addHeader("Content-Type", "application/json")
                .post(body)
                .build();
        
        try (Response response = httpClient.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                log.error("OpenAI API error: {}", response.code());
                throw new IOException("API request failed with code: " + response.code());
            }
            
            String responseBody = response.body().string();
            Map<String, Object> responseMap = objectMapper.readValue(responseBody, Map.class);
            
            // Extract the assistant's message
            Object choices = responseMap.get("choices");
            if (choices instanceof java.util.List) {
                java.util.List<?> choicesList = (java.util.List<?>) choices;
                if (!choicesList.isEmpty()) {
                    Map<String, Object> choice = (Map<String, Object>) choicesList.get(0);
                    Map<String, Object> messageMap = (Map<String, Object>) choice.get("message");
                    return (String) messageMap.get("content");
                }
            }
            
            return "Unable to parse response";
        }
    }
    
    private String buildExplanationPrompt(String code, String language) {
        return String.format(
            "You are a code explanation assistant.\n\n" +
            "Analyze this %s code and explain it.\n\n" +
            "STRICT RULES:\n" +
            "1. Do NOT repeat the code.\n" +
            "2. Do NOT include 'Code:' section.\n" +
            "3. No markdown.\n" +
            "4. No bold text.\n" +
            "5. Keep under 100 words.\n" +
            "6. Keep each section separate.\n\n" +
            "Return exactly in this format:\n\n" +
            "Explanation:\n" +
            "<2-3 short sentences>\n\n" +
            "Functions:\n" +
            "<functions used>\n\n" +
            "Loops:\n" +
            "<loops used>\n\n" +
            "Conditions:\n" +
            "<conditions used>\n\n" +
            "Variables:\n" +
            "<important variables>\n\n" +
            "Input code:\n%s",
            language, code
        );
    }
    
    private String buildOptimizationPrompt(String code, String language) {
        return String.format(
            "Optimize this %s code.\n\n" +
            "STRICT RULES:\n" +
            "1. No markdown\n" +
            "2. No ** symbols\n" +
            "3. No triple backticks\n" +
            "4. Keep response short and clean\n\n" +
            "Return in this format:\n\n" +
            "Optimized Code:\n" +
            "<optimized code>\n\n" +
            "Improvements:\n" +
            "- point 1\n" +
            "- point 2\n\n" +
            "Input Code:\n%s",
            language, code
        );
    }
    
    private String buildComplexityPrompt(String code, String language) {
        return String.format(
            "Analyze time and space complexity of this %s code. " +
            "Return plain text only. No markdown, no ** symbols, no bullet points. " +
            "Keep response under 150 words. " +
            "Use this format:\n\n" +
            "Time Complexity: <value>\n" +
            "\nSpace Complexity: <value>\n" +
            "\nSummary: <short explanation>\n\n" +
            "\nCode:\n%s",
            language, code
        );
    }
    
    private String getDefaultExplanation(String code, String language) {
        return "This is a code snippet written in " + language + ". The code performs various operations as shown above. " +
               "To get AI-powered explanations, please configure your OpenAI API key in application.properties.";
    }
}
