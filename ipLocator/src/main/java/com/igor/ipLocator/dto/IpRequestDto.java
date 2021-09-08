package com.igor.ipLocator.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class IpRequestDto {
    private String localIp;
    private String informedIp;
}
