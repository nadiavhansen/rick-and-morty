package com.rickandmorty.api.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProcessStatus {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String status;

    private LocalDateTime startTime;
    
    private LocalDateTime endTime;

    private String filePath;
    
    private String fileName;
    
    

}
