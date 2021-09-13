package com.igor.ipLocator.service;

import com.igor.ipLocator.dto.GeolocationIp;
import com.igor.ipLocator.dto.LocatorRequestDto;
import com.igor.ipLocator.dto.LocatorResponseDto;
import com.igor.ipLocator.exception.GeolocationRequestException;
import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class IpLocatorServiceTest {
    private static final String GEOLOCATION_API_KEY = "847b45eec90c4566978cbaeee2c1252c";

    private IPGeolocationAPI api = new IPGeolocationAPI(GEOLOCATION_API_KEY);

    @Mock
    private Geolocation geolocation;

    private IpLocatorService ipLocatorService = new IpLocatorService();

    @Test
    public void givenIpAddressWhenRequestGeolocationIpThenReturnGeolocationIp() throws GeolocationRequestException {
        String ip = "1.1.1.1";
        GeolocationParams geoParams = new GeolocationParams();
        geoParams.setIPAddress(ip);
        geoParams.setFields("geo,time_zone,currency");
        GeolocationIp geolocationIp = ipLocatorService.requestGeolocationIp(ip, api);

        assertEquals(geolocationIp.getCity(), "Brisbane");
        assertEquals(geolocationIp.getCountry(), "Australia");
        assertEquals(geolocationIp.getTimezone(), "Australia/Brisbane");
    }

    @Test
    public void givenLocalAndRequestedIpAddressesWhenRequestGeolocationIpThenLocatorResponseDto() throws GeolocationRequestException {
        String localIp = "1.1.1.1";
        String requestedIp = "2.2.2.2";
        LocatorRequestDto locatorRequestDto = new LocatorRequestDto(localIp, requestedIp);

        LocatorResponseDto locatorResponseDto = ipLocatorService.retrieveGeolocationInformation(locatorRequestDto);

        assertEquals(locatorResponseDto.getGeolocationIps().size(), 2);
        assertEquals(locatorResponseDto.getGeolocationIps().get(0).getCity(), "Brisbane");
        assertEquals(locatorResponseDto.getGeolocationIps().get(1).getCity(), "Lyon");
    }
}