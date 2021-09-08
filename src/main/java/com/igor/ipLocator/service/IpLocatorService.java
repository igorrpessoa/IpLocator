package com.igor.ipLocator.service;

import com.igor.ipLocator.dto.IpRequestDto;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.*;
import java.net.http.HttpRequest;

@Service
public class IpLocatorService {
    private static final String GEOLOCATION_URL = "https://api.ipgeolocation.io/getip";

    public void retrieveGeolocationInformation(IpRequestDto ipRequestDto) {

        var request = HttpRequest.newBuilder(
                    URI.create(GEOLOCATION_URL))
            .header("accept", "application/json")
            .build();

    }
}
