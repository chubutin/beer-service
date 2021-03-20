package chubutin.springframework.beerservice.web.controller;

import chubutin.springframework.beerservice.web.model.BeerDto;
import chubutin.springframework.beerservice.web.model.BeerStyleEnum;
import chubutin.springframework.beerservice.web.service.BeerServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
//these classes are loaded by hand because WebMvcTest only loads RestControllers
@ContextConfiguration(classes = { BeerServiceImpl.class, BeerController.class})
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerNotFound() throws Exception{
        mockMvc.perform(get("/api/v1/beer" + UUID.randomUUID()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer")).andExpect(status().isOk());
    }

    @Test
    void saveBeer() throws  Exception{
        BeerDto beerDto = BeerDto.builder().beerName("Heineken")
                .id(UUID.randomUUID()).beerStyle(BeerStyleEnum.LAGER)
                .build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }
}