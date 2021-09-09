package com.igor.ipLocator.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class LocatorResponseDto {

    public List<GeolocationIp> geolocationIps;
}
