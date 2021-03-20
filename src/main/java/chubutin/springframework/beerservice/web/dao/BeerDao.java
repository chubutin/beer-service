package chubutin.springframework.beerservice.web.dao;

import chubutin.springframework.beerservice.web.model.BeerDto;
import chubutin.springframework.beerservice.web.model.BeerPagedList;
import chubutin.springframework.beerservice.web.model.BeerStyleEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class BeerDao {

    private static List<BeerDto> beers = new ArrayList<>();

    private static int beerCount =3;

    static {
        beers.add(BeerDto.builder().id(UUID.randomUUID()).beerName("Quilmes").beerStyle(BeerStyleEnum.LAGER).build());
        beers.add(BeerDto.builder().id(UUID.randomUUID()).beerName("Patagonia").beerStyle(BeerStyleEnum.IPA).build());
        beers.add(BeerDto.builder().id(UUID.randomUUID()).beerName("Andes").beerStyle(BeerStyleEnum.WHEAT).build());
    }

    public BeerPagedList getBeers(){
        Pageable paging = PageRequest.of(0, beerCount, Sort.by("id"));
        return new BeerPagedList(beers, paging, beerCount);
    }

    public BeerDto getBeerById(UUID id){
        for (BeerDto b : beers){
            if (b.getId().equals(id)) {
                return b;
            }
        }
        return null;
    }

    public BeerDto addBeer(BeerDto beer) {
        if (beer.getId() == null) {
            beer.setId(UUID.randomUUID());
        }
        beers.add(beer);
        beerCount+=1;
        return beer;
    }
}
