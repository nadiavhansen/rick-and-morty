package com.rickandmorty.api.controller;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.rickandmorty.api.model.ProcessStatus;
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
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
	    if (file.isEmpty()) {
	        return ResponseEntity.badRequest().body(Map.of("error", "File is empty."));
	    }

	    String fileName = uploadService.handleFileUpload(file);
	    return ResponseEntity.ok(Map.of(
	        "message", "File uploaded successfully and is being processed.",
	        "fileName", fileName,
	        "statusUrl", "/upload/status?file=" + fileName
	    ));
	}

	
	@GetMapping("/status")
    public ResponseEntity<?> getStatus(@RequestParam("file") String fileName) {
        Optional<ProcessStatus> status = processStatusRepository.findByFileName(fileName);
        if (status.isPresent()) {
            return ResponseEntity.ok(status.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Status not found for file: " + fileName);
        }
    }

}
