package chubutin.springframework.beerservice.web.service;

import chubutin.springframework.beerservice.web.dao.BeerDao;
import chubutin.springframework.beerservice.web.model.BeerPagedList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import chubutin.springframework.beerservice.web.model.BeerDto;

import java.util.UUID;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    @Autowired
    BeerDao beerDao;

    @Override
    public BeerDto getBeer(UUID beerId) {
        return beerDao.getBeerById(beerId);
    }

    @Override
    public BeerPagedList getAllBeers() {
        return beerDao.getBeers();
    }

    @Override
    public BeerDto saveBeer(BeerDto beerDto) {
        return beerDao.addBeer(beerDto);
    }


}
