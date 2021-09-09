package com.igor.ipLocator.service;

import com.igor.ipLocator.dto.GeolocationIp;
import com.igor.ipLocator.dto.LocatorRequestDto;
import com.igor.ipLocator.dto.LocatorResponseDto;
import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class IpLocatorService {

    private static final String GEOLOCATION_API_KEY = "847b45eec90c4566978cbaeee2c1252c";

    public LocatorResponseDto retrieveGeolocationInformation(LocatorRequestDto ipRequestDto) {
        IPGeolocationAPI api = new IPGeolocationAPI(GEOLOCATION_API_KEY);
        GeolocationParams geoParams = new GeolocationParams();

        List<GeolocationIp> geolocationIps = Arrays.asList(ipRequestDto.getLocalIp(), ipRequestDto.getRequestIp())
                        .stream().map(ip -> {
            geoParams.setIPAddress(ip);
            geoParams.setFields("geo,time_zone,currency");
            Geolocation geolocation = api.getGeolocation(geoParams);

            // Check if geolocation lookup was successful
            if (geolocation.getStatus() == 200) {
                return GeolocationIp.builder().
                        country(geolocation.getCountryName())
                        .localDateTime(geolocation.getTimezone().getCurrentTime())
                        .city(geolocation.getCity())
                        .timezone(geolocation.getTimezone().getName())
                        .build();
            } else {
                System.out.printf("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
            }
            return null;
        }).collect(Collectors.toList());

        return LocatorResponseDto.builder().geolocationIps(geolocationIps).build();
    }
}
