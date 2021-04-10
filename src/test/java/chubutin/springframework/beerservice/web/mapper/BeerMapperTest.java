package chubutin.springframework.beerservice.web.mapper;

import chubutin.springframework.beerservice.domain.Beer;
import chubutin.springframework.beerservice.web.mappers.BeerMapper;
import chubutin.springframework.beerservice.web.mappers.BeerMapperImpl;
import chubutin.springframework.beerservice.web.mappers.DateMapper;
import chubutin.springframework.beerservice.web.model.BeerDto;
import chubutin.springframework.beerservice.web.model.BeerStyleEnum;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.UUID;


@SpringBootTest(classes = {BeerMapperImpl.class, DateMapper.class})
public class BeerMapperTest {

    final String beerName = "Andes";
    final String beerStyle = "IPA";
    final BigDecimal beerPrice = new BigDecimal("1.10");
    final int quantityToBrew = 12;


    @Autowired
    BeerMapper beerMapper;

    @Test
    void testBeerToBeerDto() throws ParseException {

        Timestamp createdDate = generateTimestamp();
        Timestamp lastModifiedDate = generateTimestamp();

        Beer beer = Beer.builder()
                .id(UUID.randomUUID())
                .beerName("Andes")
                .createdDate(createdDate)
                .lastModifiedDate(lastModifiedDate)
                .beerStyle("IPA")
                .price(new BigDecimal("1.10"))
                .quantityToBrew(12)
                .build();

        BeerDto beerDto = beerMapper.beerToBeerDto(beer);

        assertNotNull(beerDto);
        assertEquals(beerDto.getBeerName(), beerName);
        assertEquals(beerDto.getBeerStyle().toString(), beerStyle);
        assertEquals(beerDto.getId(), beer.getId());
        assertEquals(beerDto.getPrice(), beerPrice);
        assertEquals(beerDto.getPrice(), beerPrice);
        assertEquals(offsetToTimestamp(beerDto.getCreatedDate()), createdDate);
        assertEquals(offsetToTimestamp(beerDto.getLastModifiedDate()), lastModifiedDate);
    }

    @Test
    void testBeerDtoToBeer() throws ParseException {

        OffsetDateTime createdDate = OffsetDateTime.now();
        OffsetDateTime lastModifiedDate = OffsetDateTime.now();

        BeerDto beerDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Andes")
                .createdDate(createdDate)
                .lastModifiedDate(lastModifiedDate)
                .beerStyle(BeerStyleEnum.IPA)
                .price(new BigDecimal("1.10"))
                .build();

        Beer beer = beerMapper.beerDtoToBeer(beerDto);

        assertNotNull(beerDto);
        assertEquals(beerDto.getBeerName(), beerName);
        assertEquals(beerDto.getBeerStyle().toString(), beerStyle);
        assertEquals(beerDto.getId(), beer.getId());
        assertEquals(beerDto.getPrice(), beerPrice);
        assertEquals(beerDto.getPrice(), beerPrice);
        assertEquals(offsetToTimestamp(beerDto.getCreatedDate()), beer.getCreatedDate());
        assertEquals(offsetToTimestamp(beerDto.getLastModifiedDate()), beer.getLastModifiedDate());
    }

    private Timestamp offsetToTimestamp(OffsetDateTime time){
        return Timestamp.valueOf(time.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime());

    }

    private Timestamp generateTimestamp() throws ParseException {
        Date date= new Date();
        return new Timestamp(date.getTime());
    }
}
