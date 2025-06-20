package com.rickandmorty.api.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.rickandmorty.api.model.ProcessStatus;
import com.rickandmorty.api.model.dto.ProcessStatusDTO;
import com.rickandmorty.api.model.dto.UploadResponseDTO;
import com.rickandmorty.api.repository.ProcessStatusRepository;
import com.rickandmorty.api.service.UploadService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/upload")
@RequiredArgsConstructor
public class UploadController {
	
	private final UploadService uploadService;
	private final ProcessStatusRepository processStatusRepository;
	
	@PostMapping
	public ResponseEntity<UploadResponseDTO> uploadFile(@RequestParam("file") MultipartFile file) {
	    if (file.isEmpty()) {
	        return ResponseEntity.badRequest().body(
	            UploadResponseDTO.builder()
	                .statusUrl(null)
	                .fileName(null)
	                .message("File is empty.")
	                .build()
	        );
	    }

	    UploadResponseDTO response = uploadService.handleFileUpload(file);
	    return ResponseEntity.ok(response);
	}


	
	/*@GetMapping("/status")
    public ResponseEntity<?> getStatus(@RequestParam("file") String fileName) {
        Optional<ProcessStatus> status = processStatusRepository.findByFileName(fileName);
        if (status.isPresent()) {
            return ResponseEntity.ok(status.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status not found for file: " + fileName);
        }
    }*/
	
	@GetMapping("/status")
	public ResponseEntity<ProcessStatusDTO> getUploadStatus(@RequestParam String file) {
	    ProcessStatus status = processStatusRepository.findByFileName(file)
	        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Status not found for file: " + file));

	    ProcessStatusDTO response = ProcessStatusDTO.builder()
	        .status(status.getStatus())
	        .startTime(status.getStartTime())
	        .endTime(status.getEndTime())
	        .build();

	    return ResponseEntity.ok(response);
	}


}
