package org.demo.airport.util;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import org.demo.airport.exception.ProcessException;
import org.demo.airport.model.Airports;
import org.demo.airport.model.Countries;
import org.demo.airport.model.Runways;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

import static org.demo.airport.util.Constant.*;

@Component
public class CSVToBeanTransformerUtil {

    @Value("${resource.csv.path.airports}")
     String csvAirportPath;

    @Value("${resource.csv.path.countries}")
     String csvCountriesPath;

    @Value("${resource.csv.path.runways}")
     String csvRunwaysPath;

    @Value("${resource.output.path.highestAirport}")
     String highestAirport;

    @Value("${resource.output.path.runwaysAirport}")
     String runwaysAirport;

    private CSVToBeanTransformerUtil() {
    }

    public String getCSVFilePath(String fileName) {
        String path;
        switch (fileName) {
            case AIRPORTS_CSV:
                path = csvAirportPath;

                break;
            case RUNWAYS_CSV:
                path = csvRunwaysPath;

                break;
            case COUNTRIES_CSV:
                path = csvCountriesPath;

                break;
            case TOP_N_COUNTRIES_WITH_HIGHEST_AIRPORTS:
                path = highestAirport;

                break;
            case RUNWAYS_FOR_COUNTRY_AIRPORTS:
                path = runwaysAirport;

                break;
            default:
                throw new ProcessException(String.format("No file present with name: %s.", fileName));
        }
        return path;
    }

    private  CSVReader readFromCSVFile(String fileName) {
        String path = getCSVFilePath(fileName);
        try {
            return new CSVReaderBuilder(new FileReader(new File(path)))
                    .withSkipLines(1)
                    .build();
        } catch (FileNotFoundException e) {
            throw new ProcessException(String.format("CSV File not found at path %s", path));
        }
    }

    public  List<Runways> getAllRunwaysFromCSV() {
        ColumnPositionMappingStrategy<Runways> cpmsRunways = new ColumnPositionMappingStrategy<>();
        cpmsRunways.setType(Runways.class);
        cpmsRunways.setColumnMapping(RUNWAYS_COLUMNS);
        CsvToBean<Runways> csvBeanRunways = new CsvToBean<>();
        csvBeanRunways.setCsvReader(readFromCSVFile(RUNWAYS_CSV));
        csvBeanRunways.setMappingStrategy(cpmsRunways);
        return csvBeanRunways.parse();
    }

    public  List<Airports> getAllAirportFromCSV() {
        ColumnPositionMappingStrategy<Airports> cpmsAirport = new ColumnPositionMappingStrategy<>();
        cpmsAirport.setType(Airports.class);
        cpmsAirport.setColumnMapping(AIRPORT_COLUMNS);
        CsvToBean<Airports> csvBeanAirports = new CsvToBean<>();
        csvBeanAirports.setCsvReader(readFromCSVFile(AIRPORTS_CSV));
        csvBeanAirports.setMappingStrategy(cpmsAirport);
        return csvBeanAirports.parse();
    }

    public  List<Countries> getAllCountriesFromCSV() {
        ColumnPositionMappingStrategy<Countries> cpmsCountries = new ColumnPositionMappingStrategy<>();
        cpmsCountries.setType(Countries.class);
        cpmsCountries.setColumnMapping(COUNTRIES_COLUMNS);
        CsvToBean<Countries> csvBeanCountries = new CsvToBean<>();
        csvBeanCountries.setCsvReader(readFromCSVFile(COUNTRIES_CSV));
        csvBeanCountries.setMappingStrategy(cpmsCountries);
        return csvBeanCountries.parse();
    }
}


