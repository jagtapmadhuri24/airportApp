package org.demo.airport.dataload;

import com.opencsv.CSVWriter;
import org.demo.airport.exception.ProcessException;
import org.demo.airport.util.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;

@Service
public class CountryDetailsService {
    @Value("${resource.csv.path.countries}")
    String csvCountriesPath;

    public String addCountryRecord(String[] newCountry) throws IOException {
        String status;

        CSVWriter writer = new CSVWriter(new FileWriter(csvCountriesPath, true));
        // "id", "code", "name", "continent", "wikipedia_link", "keywords"
        //"302803, IZ, Izadia, UAE, https://en.wikipedia.org/wiki/Izadia, Iza"
        if (newCountry.length != Constant.COUNTRIES_COLUMNS.length) {
            throw new ProcessException("Input and CSV column values mismatch.");
        } else {
            status = Constant.SUCCESS;
            writer.writeNext(newCountry);
            writer.close();
        }
        return status;
    }
}
