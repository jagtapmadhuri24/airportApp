package org.demo.airport.controller;

import org.demo.airport.impl.CountriesServiceImpl;
import org.demo.airport.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/country")
public class CountryController {

    @Autowired
    CountriesServiceImpl countriesServiceImpl;

    //Insert new Country
    @PostMapping("/addNew")
    public ResponseEntity<String> addNewCountry(@RequestBody String newCountryRecord) throws IOException {
        String[] record = newCountryRecord.split(",");
        String status = countriesServiceImpl.addCountryRecord(record);
        if (Constant.SUCCESS
                .equalsIgnoreCase(status)) {
            return new ResponseEntity<>("New Country record inserted.", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
