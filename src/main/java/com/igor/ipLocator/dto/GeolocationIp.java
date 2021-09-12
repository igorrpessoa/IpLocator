package com.igor.ipLocator.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class GeolocationIp {

    private String country;
    private String city;
    private String timezone;
    private String dateTime;
}
