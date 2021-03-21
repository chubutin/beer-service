package chubutin.springframework.beerservice.web.service;

import chubutin.springframework.beerservice.web.model.BeerDto;
import chubutin.springframework.beerservice.web.model.BeerPagedList;

import java.util.UUID;

public interface BeerService {

    public BeerDto getBeer(UUID beerId);
    public BeerPagedList getAllBeers();
    public BeerDto saveBeer(BeerDto beerDto);
    BeerDto updateBeer(UUID beerId, BeerDto beerDto);
    void deleteBeer(UUID beerId);
}
