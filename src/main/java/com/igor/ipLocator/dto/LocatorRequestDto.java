package com.igor.ipLocator.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LocatorRequestDto {
    private String localIp;
    private String requestIp;
}
