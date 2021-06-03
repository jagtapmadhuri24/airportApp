package org.demo.airport.controller;

import org.demo.airport.dataload.DetailsService;
import org.demo.airport.model.Runways;
import org.demo.airport.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/airport")

public class AirportController {

    @Autowired
    DetailsService countryAirportRunwayDetailsService;


    @GetMapping("/apiCheck")
    public String apiCheck() {
        return Constant.SUCCESS;
    }

    // Get Top n Countries With Highest Airports
    @GetMapping("/topNCountries/{topN}")
    public ResponseEntity<List<String>> getTopNCountries(@PathVariable("topN") Integer topN) {

        List<String> topCountries = countryAirportRunwayDetailsService.getTopCountriesWithHighestAirport(topN);
        return new ResponseEntity<>(topCountries, HttpStatus.OK);
    }

    //Get Runways Based On Country
    @GetMapping("/runways/{country}")
    public ResponseEntity<Map<String, List<Runways>>> getRunwaysForCountryAirport(@PathVariable("country") @Size(min = 2)
                                                      @Pattern(regexp = "^[a-zA-Z]+$") String country) {

        Map<String, List<Runways>> mapForRunwaysForGivenAirportRef =
                countryAirportRunwayDetailsService.getRunwaysOfAirportBasedOnCountry(country.toUpperCase());
        return new ResponseEntity<>(mapForRunwaysForGivenAirportRef, HttpStatus.OK);
    }
    @GetMapping("/count")
    public ResponseEntity<Map<String,Integer>> getCountriesAirportCount() {

        Map<String,Integer> topCountries = countryAirportRunwayDetailsService.getCountriesAirportsCount();
        return new ResponseEntity<>(topCountries, HttpStatus.OK);
    }
}
