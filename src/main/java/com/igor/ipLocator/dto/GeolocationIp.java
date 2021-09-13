package com.igor.ipLocator.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeolocationIp {

    private String country;
    private String city;
    private String timezone;
    private String dateTime;
}
