package com.example.solenglish.application.dto;


import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public abstract class GenericDTO {
    private Long id;
    private LocalDateTime createdWhen;
    private LocalDateTime deletedWhen;
    private boolean isDeleted;
}

