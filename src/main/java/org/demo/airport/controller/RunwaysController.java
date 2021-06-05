package org.demo.airport.controller;

import org.demo.airport.impl.CountryAirportRunwayDetailsServiceImpl;
import org.demo.airport.model.Runways;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/runways")
public class RunwaysController {

    @Autowired
    CountryAirportRunwayDetailsServiceImpl countryAirportRunwayDetailsServiceImpl;

    //Get Runways Based On Country
    @GetMapping("{country}")
    public ResponseEntity<Map<String, List<Runways>>> getRunwaysForCountryAirport(@PathVariable("country") @Size(min = 2)
                                                                                  @Pattern(regexp = "^[a-zA-Z]+$") String country) {
        Map<String, List<Runways>> mapForRunwaysForGivenAirportRef =
                countryAirportRunwayDetailsServiceImpl.getRunwaysOfAirportBasedOnCountry(country.toUpperCase());
        return new ResponseEntity<>(mapForRunwaysForGivenAirportRef, HttpStatus.OK);
    }


}
