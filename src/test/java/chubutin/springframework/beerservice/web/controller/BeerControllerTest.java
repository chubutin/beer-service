package chubutin.springframework.beerservice.web.controller;

import chubutin.springframework.beerservice.web.model.BeerDto;
import chubutin.springframework.beerservice.services.BeerServiceImpl;

import chubutin.springframework.beerservice.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BeerController.class)
//these classes are loaded by hand because WebMvcTest only loads RestControllers
@ContextConfiguration(classes = { BeerServiceImpl.class, BeerController.class, MvcExceptionHandler.class})
class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeer() throws Exception{
        mockMvc.perform(get("/api/v1/beer" + UUID.randomUUID()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer")).andExpect(status().isOk());
    }

    @Test
    void handlePost() throws  Exception{
        BeerDto beerDto = createBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    void handlePost400MissingBeerName() throws  Exception{
        BeerDto beerDto = BeerDto.builder()
                .beerStyle(BeerStyleEnum.IPA)
                .build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void handlePost400MissingBeerStyle() throws  Exception{
        BeerDto beerDto = BeerDto.builder()
                .beerName("Name")
                .build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isBadRequest());
    }

    @Test
    void handlePut() throws  Exception{
        BeerDto beerDto = createBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    private BeerDto createBeerDto() {
        return BeerDto.builder()
                .beerName("Heineken")
                .beerStyle(BeerStyleEnum.IPA)
                .upc(1234567890L)
                .price(new BigDecimal("1.20"))
                .build();
    }
}