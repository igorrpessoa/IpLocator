package com.igor.ipLocator.dto;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LocatorResponseDto {

    public List<GeolocationIp> geolocationIps;
}
