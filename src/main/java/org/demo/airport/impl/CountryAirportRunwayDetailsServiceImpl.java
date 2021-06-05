package org.demo.airport.impl;

import org.demo.airport.exception.ProcessException;
import org.demo.airport.model.Airports;
import org.demo.airport.model.Countries;
import org.demo.airport.model.Runways;
import org.demo.airport.service.DetailsService;
import org.demo.airport.util.CSVToBeanTransformerUtil;
import org.demo.airport.util.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CountryAirportRunwayDetailsServiceImpl implements DetailsService {

    @Autowired
    CSVToBeanTransformerUtil csvToBeanTransformerUtil;

    private Map<String, List<Airports>> getAirportsListGroupByCountryCode() {
        List<Airports> airportsList = csvToBeanTransformerUtil.getAllAirportFromCSV();
        return airportsList.stream().collect(Collectors.groupingBy(Airports::getIso_country));
    }

    private Map<String, List<Runways>> getRunwaysListGroupByAirportRef() {
        List<Runways> runwaysList = csvToBeanTransformerUtil.getAllRunwaysFromCSV();
        return runwaysList.stream()
                .collect(Collectors.groupingBy(Runways::getAirport_ref));
    }

    @Override
    public Map<String, List<Runways>> getRunwaysOfAirportBasedOnCountry(String givenCountry) {
        String country = givenCountry.length() > 2 ? getCountryString(givenCountry) : givenCountry;
        if (getCountriesByNameOrCode(Constant.COUNTRY_CODE).containsKey(country)) {
            List<Airports> airportsList = csvToBeanTransformerUtil.getAllAirportFromCSV();
            Map<String, List<Runways>> runwaysForGivenAirportRefMap = new LinkedHashMap<>();
            getRunwaysForGivenAirportRef(country).forEach((key, value) -> {
                List<Airports> airportDetails = airportsList.stream()
                        .filter(airport -> airport.getId().equalsIgnoreCase(key))
                        .collect(Collectors.toList());
                runwaysForGivenAirportRefMap.put(key + "-" + airportDetails.get(0).getName(), value);

            });
            return runwaysForGivenAirportRefMap;
        } else {
            throw new ProcessException(String.format("Country code/name %s is not present. Please enter valid country code/name.", givenCountry));
        }
    }

    @Override
    public List<String> getTopCountriesWithHighestAirport(Integer limit) {
        List<Countries> countriesList = csvToBeanTransformerUtil.getAllCountriesFromCSV();
        Map<String, List<Airports>> airportsListGroupByCountry = getAirportsListGroupByCountryCode();
        Map<String, Integer> totalAirportInsideCountry = getTotalAirportInsideCountry(airportsListGroupByCountry);
        List<String> sortedCountryToAirport = totalAirportInsideCountry.entrySet().stream()
                .sorted(Collections.reverseOrder(Map.Entry.comparingByValue()))
                .map(Map.Entry::getKey)
                .limit(limit)
                .collect(Collectors.toList());
        List<String> topCountries = new ArrayList<>();
        sortedCountryToAirport.forEach(countryName -> countriesList.stream()
                .filter(getPredicateForCountryCode(countryName))
                .map(Countries::getName).forEach(topCountries::add));
        return topCountries;
    }

    @Override
    public Map<String, Integer> getCountriesAirportsCount() {
        Map<String, List<Airports>> airportsListGroupByCountryCode = getAirportsListGroupByCountryCode();
        Map<String, Integer> totalAirportInsideCountry = getTotalAirportInsideCountry(airportsListGroupByCountryCode);
        List<Countries> listOFCountries = csvToBeanTransformerUtil.getAllCountriesFromCSV();
        Map<String, Integer> countriesAirportsCount = new LinkedHashMap<>();
        totalAirportInsideCountry.entrySet().stream().forEach(
                element -> {

                    List<Countries> countryObject = listOFCountries.stream()
                            .filter(country -> country.getCode().equalsIgnoreCase(element.getKey()))
                            .collect(Collectors.toList());
                    countriesAirportsCount.put(countryObject.get(0).getName(), element.getValue());

                }
        );

        /*Map<String, Integer> countriesAirportsCountSorted = countriesAirportsCount.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a1, a2) -> a2, LinkedHashMap::new));*/
        return countriesAirportsCount;
    }


    private Map<String, List<Runways>> getRunwaysForGivenAirportRef(String country) {
        List<String> listForAirportIds = getListForAirportIds(getAirportsListGroupByCountryCode(), country);
        Map<String, List<Runways>> runwaysForGivenAirportRefMap = new HashMap<>();
        getRunwaysListGroupByAirportRef().forEach((key, value) ->
                listForAirportIds.forEach(airportId -> {
                    if (airportId.equalsIgnoreCase(key)) {
                        runwaysForGivenAirportRefMap.put(airportId, value);
                    }
                })
        );
        return runwaysForGivenAirportRefMap;
    }

    private Map<String, Integer> getTotalAirportInsideCountry(Map<String, List<Airports>> airportsListGroupByCountry) {
        return airportsListGroupByCountry.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue().size()));
    }

    private List<String> getListForAirportIds(Map<String, List<Airports>> airportsListGroupByCountry, String givenCountry) {
        List<String> listForAirportIds = new ArrayList<>();
        airportsListGroupByCountry.entrySet().stream().filter(unfilteredMapEntry ->
                givenCountry.equalsIgnoreCase(unfilteredMapEntry.getKey()))
                .forEach(filteredMapEntry ->
                        filteredMapEntry.getValue().forEach(airports -> listForAirportIds.add(airports.getId())));

        return listForAirportIds;
    }

    private Map<String, String> getCountriesByNameOrCode(String countryNameOrCode) {
        List<Countries> countriesList = csvToBeanTransformerUtil.getAllCountriesFromCSV();
        if (Constant.COUNTRY_CODE.equalsIgnoreCase(countryNameOrCode)) {
            return countriesList.parallelStream().collect(Collectors.toMap(countries -> countries.getCode().toUpperCase(), Countries::getName));
        } else {
            return countriesList.parallelStream().collect(Collectors.toMap(countries -> countries.getName().toUpperCase(), Countries::getCode));
        }
    }

    private String getCountryString(String givenCountry) {
        String country = Constant.EMPTY_STRING;
        for (Map.Entry<String, String> entry : getCountriesByNameOrCode(Constant.COUNTRY_NAME).entrySet()) {
            if (entry.getKey().startsWith(givenCountry)) {
                country = entry.getValue();
            }
        }
        return country;
    }
}
