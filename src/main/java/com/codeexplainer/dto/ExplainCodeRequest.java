package com.codeexplainer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExplainCodeRequest {
    private String code;
    private String language; // python or javascript
    private String title;
    private boolean includeOptimization; // bonus feature
    private boolean includeComplexity; // bonus feature
}
