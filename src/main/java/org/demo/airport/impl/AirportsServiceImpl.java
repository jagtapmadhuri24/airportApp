package org.demo.airport.impl;

import com.opencsv.CSVWriter;
import org.demo.airport.exception.ProcessException;
import org.demo.airport.service.AirportsService;
import org.demo.airport.util.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class AirportsServiceImpl implements AirportsService {

    @Value("${resource.csv.path.airports}")
    String csvAirportsPath;


    @Override
    public String addAirportRecord(String[] newAirport) throws IOException {
        String status;

        CSVWriter writer = new CSVWriter(new FileWriter(csvAirportsPath, true));

        if (newAirport.length != Constant.AIRPORT_COLUMNS.length) {
            throw new ProcessException("Input and CSV column values mismatch.");
        } else {
            status = Constant.SUCCESS;
            writer.writeNext(newAirport);
            writer.close();
        }
        return status;

    }
}
