package com.igor.ipLocator.controller;

import com.igor.ipLocator.IpLocatorApplication;
import com.igor.ipLocator.dto.GeolocationIp;
import com.igor.ipLocator.dto.LocatorRequestDto;
import com.igor.ipLocator.dto.LocatorResponseDto;
import com.igor.ipLocator.exception.GeolocationRequestException;
import com.igor.ipLocator.service.IpLocatorService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@WebMvcTest(IpLocatorController.class)
class IpLocatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IpLocatorService ipLocatorService;

    @Test
    public void whenGetRetrieveGeolocationThenReturnGeolocationIpsListJson() throws Exception {
        String responseJson = "{\"geolocationIps\":[{\"country\":null,\"city\":\"Brisbane\",\"timezone\":null,\"dateTime\":null},{\"country\":null,\"city\":\"Lyon\",\"timezone\":null,\"dateTime\":null}]}";
        List<GeolocationIp> geolocationIps = new ArrayList<>();
        geolocationIps.add(GeolocationIp.builder().city("Brisbane").build());
        geolocationIps.add(GeolocationIp.builder().city("Lyon").build());
        LocatorResponseDto dto = new LocatorResponseDto(geolocationIps);
        Mockito.when(ipLocatorService.retrieveGeolocationInformation(ArgumentMatchers.any(LocatorRequestDto.class)))
                .thenReturn(dto);

        this.mockMvc.perform(MockMvcRequestBuilders
                .get("/api/retrieveGeolocation")
                .param("localIp","1.1.1.1")
                .param("requestIp","2.2.2.2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().string(responseJson));

    }
}