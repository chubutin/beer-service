package chubutin.springframework.beerservice.web.controller;

import chubutin.springframework.beerservice.domain.Beer;
import chubutin.springframework.beerservice.repository.BeerRepository;
import chubutin.springframework.beerservice.web.model.BeerDto;

import chubutin.springframework.beerservice.web.model.BeerStyleEnum;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "http", uriHost = "localhost", uriPort = 8080)
@WebMvcTest(BeerController.class)
@ComponentScan(basePackages = "chubutin.springframework.beerservice.web.mappers")
public class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerRepository beerRepository;

    @Test
    void getBeerById() throws Exception {
        given(beerRepository.findById(any())).willReturn(Optional.of(createBeer()));

        mockMvc.perform(get("/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .param("isCold", "yes")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                                parameterWithName("beerId").description("UUID of desired beer to get")
                        ),
                        requestParameters(
                                parameterWithName("isCold").description("Is Beer Cold query param")
                        ),
                        responseFields(
                                fieldWithPath("id").description("Id of Beer"),
                                fieldWithPath("version").description("Version number"),
                                fieldWithPath("createdDate").description("Date Created"),
                                fieldWithPath("lastModifiedDate").description("Date Updated"),
                                fieldWithPath("beerName").description("Beer Name"),
                                fieldWithPath("beerStyle").description("Beer Style"),
                                fieldWithPath("upc").description("UPC of Beer"),
                                fieldWithPath("price").description("Price"),
                                fieldWithPath("quantityOnHand").description("Quantity On hand"),
                                fieldWithPath("minOnHand").description("Quantity On hand"),
                                fieldWithPath("quantityToBrew").description("Quantity On hand")
                        )));
    }

    @Test
    void getAllBeers() throws Exception {
        List<Beer> beers = new ArrayList<Beer>();
        beers.add(Beer.builder().build());
        given(beerRepository.findAll()).willReturn(beers);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);

        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-all",
                        responseFields(
                                fieldWithPath("[].id").description("Id of Beer"),
                                fieldWithPath("[].version").description("Version number"),
                                fieldWithPath("[].createdDate").description("Date Created"),
                                fieldWithPath("[].lastModifiedDate").description("Date Updated"),
                                fieldWithPath("[].beerName").description("Beer Name"),
                                fieldWithPath("[].beerStyle").description("Beer Style"),
                                fieldWithPath("[].upc").description("UPC of Beer"),
                                fieldWithPath("[].price").description("Price"),
                                fieldWithPath("[].quantityOnHand").description("Quantity On hand"),
                                fieldWithPath("[].minOnHand").description("Quantity On hand"),
                                fieldWithPath("[].quantityToBrew").description("Quantity On hand")
                        )));
    }

    @Test
    void saveNewBeer() throws Exception {
        BeerDto beerDto = createBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        ConstrainedFields fields = new ConstrainedFields(BeerDto.class);
        Beer beer = createBeer();
        beer.setId(UUID.randomUUID());
        given(beerRepository.save(any())).willReturn(beer);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
                .andDo(document("v1/beer-new",
                        requestFields(
                                fields.withPath("id").description("Id of Beer").ignored(),
                                fields.withPath("version").description("Version number").ignored(),
                                fields.withPath("createdDate").description("Date Created").ignored(),
                                fields.withPath("lastModifiedDate").description("Date Updated").ignored(),
                                fields.withPath("beerName").description("Beer Name"),
                                fields.withPath("beerStyle").description("Beer Style"),
                                fields.withPath("upc").description("UPC of Beer"),
                                fields.withPath("price").description("Price"),
                                fields.withPath("minOnHand").description("Minimum On hand"),
                                fields.withPath("quantityOnHand").description("Quantity On hand"),
                                fields.withPath("quantityToBrew").description("Quantity to brew")
                        )));
    }

//    @Test
//    void handlePost400MissingBeerName() throws  Exception{
//        BeerDto beerDto = BeerDto.builder()
//                .beerStyle(BeerStyleEnum.IPA)
//                .build();
//        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
//
//        mockMvc.perform(post("/api/v1/beer")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(beerDtoJson))
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void handlePost400MissingBeerStyle() throws  Exception{
//        BeerDto beerDto = BeerDto.builder()
//                .beerName("Name")
//                .build();
//        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
//
//        mockMvc.perform(post("/api/v1/beer")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(beerDtoJson))
//                .andExpect(status().isBadRequest());
//    }

    @Test
    void updateBeerById() throws Exception {
        BeerDto beerDto = createBeerDto();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(put("/api/v1/beer/" + UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/" + UUID.randomUUID().toString()))
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

    private Beer createBeer() {
        return Beer.builder()
                .beerName("Heineken")
                .beerStyle("IPA")
                .upc(1234567890L)
                .price(new BigDecimal("1.20"))
                .quantityToBrew(2)
                .quantityToBrew(5)
                .minOnHand(1)
                .build();
    }

    private static class ConstrainedFields {

        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input) {
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path) {
            return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                    .collectionToDelimitedString(this.constraintDescriptions
                            .descriptionsForProperty(path), ". ")));
        }
    }
}