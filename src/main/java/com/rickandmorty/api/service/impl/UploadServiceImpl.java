package com.rickandmorty.api.service.impl;

import com.rickandmorty.api.model.ProcessStatus;
import com.rickandmorty.api.model.UploadHistory;
import com.rickandmorty.api.model.dto.UploadResponseDTO;
import com.rickandmorty.api.repository.ProcessStatusRepository;
import com.rickandmorty.api.repository.UploadHistoryRepository;
import com.rickandmorty.api.service.EpisodeCsvService;
import com.rickandmorty.api.service.FileProcessingService;
import com.rickandmorty.api.service.UploadService;
import lombok.RequiredArgsConstructor;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadServiceImpl implements UploadService {

    private final UploadHistoryRepository uploadHistoryRepository;
    private final ProcessStatusRepository processStatusRepository;
    private final FileProcessingService fileProcessingService;
    private final EpisodeCsvService episodeCsvService;

    private final String uploadFolder = System.getProperty("user.dir") + File.separator + "uploads";

    @Override
    public UploadResponseDTO handleFileUpload(MultipartFile file) {
        try {
            File folder = new File(uploadFolder);
            if (!folder.exists() && !folder.mkdirs()) {
                throw new RuntimeException("Could not create upload directory: " + folder.getAbsolutePath());
            }

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            File dest = new File(uploadFolder + File.separator + fileName);
            file.transferTo(dest);

            UploadHistory history = UploadHistory.builder()
                    .filePath(dest.getAbsolutePath())
                    .createdTimestamp(LocalDateTime.now())
                    .build();
            uploadHistoryRepository.save(history);

            ProcessStatus status = ProcessStatus.builder()
                    .startTime(LocalDateTime.now())
                    .status("PENDING")
                    .filePath(dest.getAbsolutePath())
                    .fileName(fileName)
                    .build();
            processStatusRepository.save(status);
            processFileAsync(fileName, dest, status.getId());
            
            String statusUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/upload/status")
                    .queryParam("file", fileName)
                    .toUriString();

            return UploadResponseDTO.builder()
                    .statusUrl(statusUrl)
                    .fileName(fileName)
                    .message("File uploaded successfully and is being processed.")
                    .build();

        } catch (IOException e) {
            throw new RuntimeException("Error trying to save the file", e);
        }
    }

    @Async
    public void processFileAsync(String fileName, File file, Long statusId) {
        ProcessStatus status = processStatusRepository.findById(statusId).orElseThrow();
        status.setStatus("PROCESSING");
        processStatusRepository.save(status);

        try {
            if (fileName.toLowerCase().endsWith("episodes.csv")) {
                try (FileReader fr = new FileReader(file)) {
                    episodeCsvService.process(fr);
                }
            } else {
                fileProcessingService.processFile(file.getAbsolutePath());
            }
            status.setStatus("SUCCESS");
        } catch (Exception e) {
            status.setStatus("FAILED");
            System.err.println("Erro ao processar arquivo: " + e.getMessage());
        }

        status.setEndTime(LocalDateTime.now());
        processStatusRepository.save(status);
    }

	@Override
	public String getLastUploadedFilePath() {
		return uploadHistoryRepository.findTopByOrderByCreatedTimestampDesc()
        .map(UploadHistory::getFilePath)
        .orElse(null);

	}
}
