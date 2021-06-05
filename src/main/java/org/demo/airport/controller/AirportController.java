package org.demo.airport.controller;

import org.demo.airport.impl.AirportsServiceImpl;
import org.demo.airport.impl.CountryAirportRunwayDetailsServiceImpl;
import org.demo.airport.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/airport")

public class AirportController {

    @Autowired
    CountryAirportRunwayDetailsServiceImpl countryAirportRunwayDetailsServiceImpl;

    @Autowired
    AirportsServiceImpl airportDetailsServiceImpl;


    @GetMapping("/apiCheck")
    public String apiCheck() {
        return Constant.SUCCESS;
    }

    // Get Top n Countries With Highest Airports
    @GetMapping("/topNCountries/{topN}")
    public ResponseEntity<List<String>> getTopNCountries(@PathVariable("topN") Integer topN) {

        List<String> topCountries = countryAirportRunwayDetailsServiceImpl.getTopCountriesWithHighestAirport(topN);
        return new ResponseEntity<>(topCountries, HttpStatus.OK);
    }


    @GetMapping("/count")
    public ResponseEntity<Map<String, Integer>> getCountriesAirportCount() {

        Map<String, Integer> topCountries = countryAirportRunwayDetailsServiceImpl.getCountriesAirportsCount();
        return new ResponseEntity<>(topCountries, HttpStatus.OK);
    }

    //Insert new Airport
    @PostMapping("/addNew")
    public ResponseEntity<String> addNewAirport(@RequestBody String newAirportRecord) throws IOException {
        String[] record = newAirportRecord.split(",");
        String status = airportDetailsServiceImpl.addAirportRecord(record);
        if (status.equalsIgnoreCase(Constant.SUCCESS)) {
            return new ResponseEntity<>("New Airport record inserted.", HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
