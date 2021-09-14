package com.igor.ipLocator.controller;

import com.igor.ipLocator.dto.LocatorRequestDto;
import com.igor.ipLocator.dto.LocatorResponseDto;
import com.igor.ipLocator.exception.GeolocationRequestException;
import com.igor.ipLocator.service.IpLocatorService;
import lombok.NonNull;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/api")
@Validated
public class IpLocatorController {

    private static final String REGEX_IPv4IPv6 =
            "((^\\s*((([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5]))\\s*$)|(^\\s*((([0-9A-Fa-f]{1,4}:){7}([0-9A-Fa-f]{1,4}|:))|(([0-9A-Fa-f]{1,4}:){6}(:[0-9A-Fa-f]{1,4}|((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){5}(((:[0-9A-Fa-f]{1,4}){1,2})|:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3})|:))|(([0-9A-Fa-f]{1,4}:){4}(((:[0-9A-Fa-f]{1,4}){1,3})|((:[0-9A-Fa-f]{1,4})?:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){3}(((:[0-9A-Fa-f]{1,4}){1,4})|((:[0-9A-Fa-f]{1,4}){0,2}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){2}(((:[0-9A-Fa-f]{1,4}){1,5})|((:[0-9A-Fa-f]{1,4}){0,3}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(([0-9A-Fa-f]{1,4}:){1}(((:[0-9A-Fa-f]{1,4}){1,6})|((:[0-9A-Fa-f]{1,4}){0,4}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:))|(:(((:[0-9A-Fa-f]{1,4}){1,7})|((:[0-9A-Fa-f]{1,4}){0,5}:((25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)(\\.(25[0-5]|2[0-4]\\d|1\\d\\d|[1-9]?\\d)){3}))|:)))(%.+)?\\s*$))";
    Logger logger = LoggerFactory.getLogger(IpLocatorController.class);

    @Autowired
    public IpLocatorController(IpLocatorService ipLocatorService){
        this.ipLocatorService = ipLocatorService;
    }

    private IpLocatorService ipLocatorService;

    /*
    * Retrieve geolocation for ips passed as parameter
    *
    * @param localIp - Local Ip from the user
    * @param requestIp - Ip requested to compare with Local Ip
    *
    * @return:
    *   OK If successful
    *   BAD REQUEST If input validation error
    *   INTERNAL SERVER ERROR If geolocationIp API request fails
    *
    * @author Igor Pessoa
    * */
    @GetMapping(value= "/retrieveGeolocation",
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<LocatorResponseDto> ipGeolocationRetrieval(
    @RequestParam @NonNull @Pattern(regexp = REGEX_IPv4IPv6) String localIp,
    @RequestParam @NonNull @Pattern(regexp = REGEX_IPv4IPv6) String requestIp) throws GeolocationRequestException {
        logger.debug("Received Request for geolocations {} and {}", localIp, requestIp);
        LocatorRequestDto locatorRequestDto = LocatorRequestDto
                .builder()
                .requestIp(requestIp)
                .localIp(localIp)
                .build();
        LocatorResponseDto responseDto = ipLocatorService.retrieveGeolocationInformation(locatorRequestDto);
        return ResponseEntity.ok(responseDto);
    }
}