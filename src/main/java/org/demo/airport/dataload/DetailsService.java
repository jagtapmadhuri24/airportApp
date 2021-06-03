package org.demo.airport.dataload;

import org.demo.airport.model.Countries;
import org.demo.airport.model.Runways;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public interface DetailsService {

     Map<String, List<Runways>> getRunwaysOfAirportBasedOnCountry(String givenCountry);

     List<String> getTopCountriesWithHighestAirport(Integer limit);

     Map<String, Integer> getCountriesAirportsCount();

      default Predicate<Countries> getPredicateForCountryCode(String countryName){
          return  (country -> country.getCode().equalsIgnoreCase(countryName));

     }
}
