package com.rickandmorty.api.service.impl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

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

    @Override
    public void process(FileReader fr) {
        try (BufferedReader br = new BufferedReader(fr);
             CSVReader csvReader = new CSVReader(br)) {

            String[] fields;
            int rowNumber = 0;
            csvReader.readNext();

            List<String[]> lines = new ArrayList<>();

            while ((fields = csvReader.readNext()) != null) {
                lines.add(fields);
            }

            for (String[] line : lines) {
            	rowNumber++;
                if (line.length < 4) {
                    //System.out.println("Row " + rowNumber + " ignored: insufficient number of fields.");
                    continue;
                }
                if (line[0].trim().isEmpty() || line[1].trim().isEmpty() || line[3].trim().isEmpty()) {
                    //System.out.println("Row " + rowNumber + " ignored: empty numeric field.");
                    continue;
                }

                int episodeId = Integer.parseInt(line[0].trim());
                int characterId = Integer.parseInt(line[1].trim());
                int locationId = Integer.parseInt(line[3].trim());

                rickAndMortyApiService.getASingleEpisode(episodeId);       
                rickAndMortyApiService.getASingleCharacter(characterId);
                rickAndMortyApiService.getASingleLocation(locationId);
            }

            rowNumber = 0;
            for (String[] line : lines) {
                rowNumber++;

                if (line.length < 4) {
                    //System.out.println("Row " + rowNumber + " ignored: insufficient number of fields.");
                    continue;
                }
                if (line[0].trim().isEmpty() || line[1].trim().isEmpty() || line[3].trim().isEmpty()) {
                    //System.out.println("Row " + rowNumber + " ignored: empty numeric field.");
                    continue;
                }

                try {
                    int episodeId = Integer.parseInt(line[0].trim());
                    int characterId = Integer.parseInt(line[1].trim());
                    String characterName = line[2].trim();
                    int locationId = Integer.parseInt(line[3].trim());

                    episodeService.linkCharacterToEpisode(episodeId, characterId, characterName, locationId);

                } catch (NumberFormatException e) {
                    System.out.println("Erro no parse da linha " + rowNumber + ": " + e.getMessage());
                }
            }

        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("Falha ao processar o arquivo CSV: " + e.getMessage(), e);
        }
    }


}
