package com.rickandmorty.api.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import com.rickandmorty.api.service.EpisodeCsvService;
import com.rickandmorty.api.service.EpisodeService;
import com.rickandmorty.api.service.RickAndMortyApiService;

@Service
public class EpisodeCsvServiceImpl implements EpisodeCsvService {

    private final EpisodeService episodeService;
    private final RickAndMortyApiService rickAndMortyApiService;

    public EpisodeCsvServiceImpl(EpisodeService episodeService, RickAndMortyApiService rickAndMortyApiService) {
        this.episodeService = episodeService;
        this.rickAndMortyApiService = rickAndMortyApiService;
    }

    /*@Override
    public void process(MultipartFile file) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));
             CSVReader csvReader = new CSVReader(br)) {

            String[] fields;
            int lineNumber = 0;

            // Pula o cabeçalho
            csvReader.readNext();

            while ((fields = csvReader.readNext()) != null) {
                lineNumber++;

                if (fields.length < 4) {
                    System.out.println("Linha " + lineNumber + " ignorada: número insuficiente de campos.");
                    continue;
                }

                try {
                    int episodeId = Integer.parseInt(fields[0].trim());
                    int characterId = Integer.parseInt(fields[1].trim());
                    String characterName = fields[2].trim();
                    int locationId = Integer.parseInt(fields[3].trim());

                    episodeService.linkCharacterToEpisode(episodeId, characterId, characterName, locationId);

                } catch (NumberFormatException e) {
                    System.out.println("Erro no parse da linha " + lineNumber + ": " + e.getMessage());
                }
            }

        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Falha ao processar o arquivo CSV: " + e.getMessage(), e);
        }
    }*/

    @Override
    public void process(FileReader fr) {
        try (BufferedReader br = new BufferedReader(fr);
             CSVReader csvReader = new CSVReader(br)) {

            String[] fields;
            int lineNumber = 0;

            // Pula o cabeçalho
            csvReader.readNext();

            List<String[]> lines = new ArrayList<>();

            // 1. Ler tudo e armazenar
            while ((fields = csvReader.readNext()) != null) {
                lines.add(fields);
            }

            // 2. Primeiro, garantir que todos os episódios e personagens existam no banco
            for (String[] line : lines) {
                if (line.length < 4) continue;

                int episodeId = Integer.parseInt(line[0].trim());
                int characterId = Integer.parseInt(line[1].trim());
                int locationId = Integer.parseInt(line[3].trim());

                rickAndMortyApiService.getASingleEpisode(episodeId);       // garante que o episódio existe
                rickAndMortyApiService.getASingleCharacter(characterId); // garante que o personagem existe
                rickAndMortyApiService.getASingleLocation(locationId);
            }

            // 3. Agora que os dados já estão no banco, faz os links
            lineNumber = 0;
            for (String[] line : lines) {
                lineNumber++;

                if (line.length < 4) {
                    System.out.println("Linha " + lineNumber + " ignorada: número insuficiente de campos.");
                    continue;
                }

                try {
                    int episodeId = Integer.parseInt(line[0].trim());
                    int characterId = Integer.parseInt(line[1].trim());
                    String characterName = line[2].trim();
                    int locationId = Integer.parseInt(line[3].trim());

                    episodeService.linkCharacterToEpisode(episodeId, characterId, characterName, locationId);

                } catch (NumberFormatException e) {
                    System.out.println("Erro no parse da linha " + lineNumber + ": " + e.getMessage());
                }
            }

        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Falha ao processar o arquivo CSV: " + e.getMessage(), e);
        }
    }


}
