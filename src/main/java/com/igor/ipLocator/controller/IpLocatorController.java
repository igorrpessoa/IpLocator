package com.igor.ipLocator.controller;

import com.igor.ipLocator.dto.LocatorRequestDto;
import com.igor.ipLocator.dto.LocatorResponseDto;
import com.igor.ipLocator.service.IpLocatorService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

@RestController
@RequestMapping("/api")
public class IpLocatorController {

    Logger logger = LoggerFactory.getLogger(IpLocatorController.class);

    @Autowired
    public IpLocatorController(IpLocatorService ipLocatorService){
        this.ipLocatorService = ipLocatorService;
    }

    private IpLocatorService ipLocatorService;

    @GetMapping(value= "/retrieveGeolocation",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LocatorResponseDto> ipGeolocationRetrieval(@RequestParam String localIp,
                                                                     @RequestParam String requestIp) {
        logger.debug("Received Request for geolocations {} and {}", localIp, requestIp);
        LocatorResponseDto responseDto = ipLocatorService.retrieveGeolocationInformation(LocatorRequestDto
                .builder()
                .requestIp(requestIp)
                .localIp(localIp)
                .build());
        return ResponseEntity.ok(responseDto);
    }
}