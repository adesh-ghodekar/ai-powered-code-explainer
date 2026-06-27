package com.codeexplainer.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "code_snippets")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CodeSnippet {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(length = 5000)
    private String code;
    
    @Column(length = 50)
    private String language; // python, javascript
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String explanation;
    
    @Column(length = 500)
    private String title;
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String complexity; // time/space complexity if detected
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String optimizedCode;
    
    @Lob
    @Column(columnDefinition = "TEXT")
    private String optimizationNotes;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private Integer tokensUsed;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
