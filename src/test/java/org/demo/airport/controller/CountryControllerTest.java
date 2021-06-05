package org.demo.airport.controller;

import org.demo.airport.impl.CountriesServiceImpl;
import org.demo.airport.util.Constant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;


@RunWith(MockitoJUnitRunner.class)
public class CountryControllerTest {

    @Mock
    CountriesServiceImpl countriesServiceImplMock;

    @InjectMocks
    CountryController underTest;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void addNewCountry() throws IOException {
        String[] record = {"302803", "IZ", "Izadia", "UAE", "https://en.wikipedia.org/wiki/Izadia", "Iza"};

        Mockito.when(countriesServiceImplMock.addCountryRecord(Mockito.any())).thenReturn(Constant.SUCCESS);
        ResponseEntity<String> responseEntity =
                underTest.addNewCountry("302803, IZ, Izadia, UAE, https://en.wikipedia.org/wiki/Izadia, Iza");
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
}