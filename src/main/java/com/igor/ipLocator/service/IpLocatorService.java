package com.igor.ipLocator.service;

import ch.qos.logback.core.net.ObjectWriter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.igor.ipLocator.controller.IpLocatorController;
import com.igor.ipLocator.dto.GeolocationIp;
import com.igor.ipLocator.dto.LocatorRequestDto;
import com.igor.ipLocator.dto.LocatorResponseDto;
import com.igor.ipLocator.exception.GeolocationRequestException;
import io.ipgeolocation.api.Geolocation;
import io.ipgeolocation.api.GeolocationParams;
import io.ipgeolocation.api.IPGeolocationAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class IpLocatorService {

    private ObjectMapper mapper = new ObjectMapper();
    Logger logger = LoggerFactory.getLogger(IpLocatorService.class);

    private static final String GEOLOCATION_API_KEY = "847b45eec90c4566978cbaeee2c1252c";

    public LocatorResponseDto retrieveGeolocationInformation(LocatorRequestDto ipRequestDto) throws GeolocationRequestException {
        List<GeolocationIp> geolocationIps = new ArrayList<>();
        logger.debug("Request for IPGeolocationAPI for LocalIP {}", ipRequestDto.getLocalIp());
        geolocationIps.add(requestGeolocationIp(ipRequestDto.getLocalIp()));

        logger.debug("Request for IPGeolocationAPI for RequestIP {}", ipRequestDto.getRequestIp());
        geolocationIps.add(requestGeolocationIp(ipRequestDto.getRequestIp()));

        return LocatorResponseDto.builder().geolocationIps(geolocationIps).build();
    }

    private GeolocationIp requestGeolocationIp(String ip) throws GeolocationRequestException{
        IPGeolocationAPI api = new IPGeolocationAPI(GEOLOCATION_API_KEY);
        GeolocationParams geoParams = new GeolocationParams();
        geoParams.setIPAddress(ip);
        geoParams.setFields("geo,time_zone,currency");
        try{
            Geolocation geolocation = api.getGeolocation(geoParams);

            // Check if geolocation lookup was successful
            if (geolocation.getStatus() == 200) {
                String json = mapper.writeValueAsString(geolocation);
                logger.debug("{}", json);
                return GeolocationIp.builder().
                        country(geolocation.getCountryName())
                        .dateTime(geolocation.getTimezone().getCurrentTime())
                        .city(geolocation.getCity())
                        .timezone(geolocation.getTimezone().getName())
                        .build();
            } else {
                logger.error("Status Code: %d, Message: %s\n", geolocation.getStatus(), geolocation.getMessage());
                throw new GeolocationRequestException(geolocation.getMessage());
            }
        } catch (JsonProcessingException e){
            logger.error("Was not possible to parse GeolocationAPI response to JSON");
        }
        return null;
    }
}
