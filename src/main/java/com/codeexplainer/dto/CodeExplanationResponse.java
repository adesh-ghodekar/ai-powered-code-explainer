package com.codeexplainer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeExplanationResponse {
    private Long id;
    private String code;
    private String language;
    private String title;
    private String explanation;
    private String complexity;
    private String optimizedCode;
    private String optimizationNotes;
    private Integer tokensUsed;
    private String createdAt;
}
