package com.igor.ipLocator.controller;

import com.igor.ipLocator.dto.IpRequestDto;
import com.igor.ipLocator.service.IpLocatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IpLocatorController {

    @Autowired
    public IpLocatorController(IpLocatorService ipLocatorService){
        this.ipLocatorService = ipLocatorService;
    }

    private IpLocatorService ipLocatorService;

    @GetMapping(value= "/retrieveGeolocation",
    consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity ipGeolocationRetrieval(@RequestBody IpRequestDto ipRequestDto) {
        ipLocatorService.retrieveGeolocationInformation(ipRequestDto);
        return ResponseEntity.ok().build();
    }
}