package com.rickandmorty.api.model.dto;

import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResponseDTO {
	
	private String statusUrl;
    private String fileName;
    private String message;

}
