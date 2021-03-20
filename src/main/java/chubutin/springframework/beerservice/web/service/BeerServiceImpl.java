package chubutin.springframework.beerservice.web.service;

import chubutin.springframework.beerservice.web.model.BeerPagedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import chubutin.springframework.beerservice.web.model.BeerDto;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    @Override
    public BeerDto getBeer(UUID beerId) {
        return BeerDto.builder().id(beerId).build();
    }

    @Override
    public BeerPagedList getAllBeers() {
        return null;
    }

    @Override
    public BeerDto saveBeer(BeerDto beerDto) {
        return BeerDto.builder().id(UUID.randomUUID()).build();
    }

}
