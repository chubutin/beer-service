package chubutin.springframework.beerservice.web.controller;

import chubutin.springframework.beerservice.web.model.BeerDto;
import chubutin.springframework.beerservice.web.model.BeerPagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import chubutin.springframework.beerservice.web.service.BeerService;

import java.util.UUID;

@RequestMapping(value = "/api/v1/beer")
@RestController
public class BeerController {

    @Autowired
    BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity getBeer(@PathVariable("beerId") UUID beerId) {
        BeerDto beer = beerService.getBeer(beerId);
        return new ResponseEntity(beer, (beer != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity getBeers() {
        return new ResponseEntity(beerService.getAllBeers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveBeer(@RequestBody BeerDto beerDto) {
        return new ResponseEntity(beerService.saveBeer(beerDto), HttpStatus.CREATED);
    }
}
