package com.igor.ipLocator.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Builder
@Getter
@AllArgsConstructor
public class LocatorRequestDto {
    @NonNull
    private String localIp;
    @NonNull
    private String requestIp;
}
