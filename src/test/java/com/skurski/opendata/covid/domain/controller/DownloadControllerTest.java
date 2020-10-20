package com.skurski.opendata.covid.domain.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(controllers = DownloadController.class)
class DownloadControllerTest {

    private static final String DOWNLOAD_CONTROLLER_ENDPOINT = "/covid/download";
    private static final String RESPONSE_MESSAGE = "Covid CSV file successfully downloaded.";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OpenDataRestClient openDataRestClient;

    @Test
    void shouldDownloadCsvFile() throws Exception {
        // given
        when(openDataRestClient.downloadCovidStatistics())
                .thenReturn("test;file;content;example".getBytes());

        // when
        mockMvc.perform(
                get(DOWNLOAD_CONTROLLER_ENDPOINT))
                .andDo(print())
                // then
                .andExpect(status().isOk())
                .andExpect(content().string(RESPONSE_MESSAGE));

        verify(openDataRestClient).downloadCovidStatistics();
    }

}