package com.rickandmorty.api.model.dto;

import java.time.LocalDateTime;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessStatusDTO {
	
    private String status;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    
}
