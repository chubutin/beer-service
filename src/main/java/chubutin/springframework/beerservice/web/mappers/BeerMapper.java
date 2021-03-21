package chubutin.springframework.beerservice.web.mappers;

import chubutin.springframework.beerservice.domain.Beer;
import chubutin.springframework.beerservice.web.model.BeerDto;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(uses = {DateMapper.class})
@Component
public interface BeerMapper {

    BeerDto beerToBeerDto(Beer beer);

    Beer beerDtoToBeer(BeerDto beerDto);

}
