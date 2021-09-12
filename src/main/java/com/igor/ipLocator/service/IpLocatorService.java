package com.igor.ipLocator.service;

import com.igor.ipLocator.controller.IpLocatorController;
import com.igor.ipLocator.dto.GeolocationIp;
import com.igor.ipLocator.dto.LocatorRequestDto;
import com.igor.ipLocator.dto.LocatorResponseDto;
import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class IpLocatorService {

    Logger logger = LoggerFactory.getLogger(IpLocatorService.class);

    private static final String GEOLOCATION_API_KEY = "847b45eec90c4566978cbaeee2c1252c";

    public LocatorResponseDto retrieveGeolocationInformation(LocatorRequestDto ipRequestDto) {
        List<GeolocationIp> geolocationIps = new ArrayList<>();

        geolocationIps.add(requestGeolocationIp(ipRequestDto.getLocalIp()));
        geolocationIps.add(requestGeolocationIp(ipRequestDto.getRequestIp()));

        return LocatorResponseDto.builder().geolocationIps(geolocationIps).build();
    }

    private GeolocationIp requestGeolocationIp(String ip) {
        IPGeolocationAPI api = new IPGeolocationAPI(GEOLOCATION_API_KEY);
        GeolocationParams geoParams = new GeolocationParams();
        geoParams.setIPAddress(ip);
        geoParams.setFields("geo,time_zone,currency");
        Geolocation geolocation = api.getGeolocation(geoParams);

        // Check if geolocation lookup was successful
        if (geolocation.getStatus() == 200) {
            return GeolocationIp.builder().
                    country(geolocation.getCountryName())
                    .dateTime(geolocation.getTimezone().getCurrentTime())
                    .city(geolocation.getCity())
                    .timezone(geolocation.getTimezone().getName())
                    .build();
        } else {
            logger.error("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
        }
        return null;
    }
}
