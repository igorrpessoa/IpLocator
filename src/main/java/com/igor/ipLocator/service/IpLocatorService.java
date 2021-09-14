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

    public IpLocatorService(){
        api = new IPGeolocationAPI(GEOLOCATION_API_KEY);
        geoParams = new GeolocationParams();
        geoParams.setFields("geo,time_zone,currency");
    }
    private ObjectMapper mapper = new ObjectMapper();
    Logger logger = LoggerFactory.getLogger(IpLocatorService.class);

    private static final String GEOLOCATION_API_KEY = "847b45eec90c4566978cbaeee2c1252c";
    private IPGeolocationAPI api;
    private GeolocationParams geoParams;

    public LocatorResponseDto retrieveGeolocationInformation(LocatorRequestDto ipRequestDto) throws GeolocationRequestException {
        List<GeolocationIp> geolocationIps = new ArrayList<>();

        logger.debug("Request for IPGeolocationAPI for LocalIP {}", ipRequestDto.getLocalIp());
        geolocationIps.add(requestGeolocationIp(ipRequestDto.getLocalIp(), api));

        logger.debug("Request for IPGeolocationAPI for RequestIP {}", ipRequestDto.getRequestIp());
        geolocationIps.add(requestGeolocationIp(ipRequestDto.getRequestIp(), api));

        return LocatorResponseDto.builder().geolocationIps(geolocationIps).build();
    }

    protected GeolocationIp requestGeolocationIp(String ip, IPGeolocationAPI api) throws GeolocationRequestException{
            geoParams.setIPAddress(ip);
            Geolocation geolocation = api.getGeolocation(geoParams);

            // Check if geolocation lookup was successful
            if (geolocation.getStatus() == 200) {
                logGeolocationPayload(geolocation);
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
    }

    private void logGeolocationPayload(Geolocation geolocation) {
        try{
            String json = mapper.writeValueAsString(geolocation);
            logger.debug("{}", json);
        } catch (JsonProcessingException e){
            logger.error("It was not possible to parse GeolocationAPI response to JSON");
        }
    }
}
