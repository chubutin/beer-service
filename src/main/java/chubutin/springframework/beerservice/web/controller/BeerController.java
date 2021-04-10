package chubutin.springframework.beerservice.web.controller;

import chubutin.springframework.beerservice.domain.Beer;
import chubutin.springframework.beerservice.repository.BeerRepository;
import chubutin.springframework.beerservice.web.mappers.BeerMapper;
import chubutin.springframework.beerservice.web.model.BeerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/beer")
@RestController
public class BeerController {

    private final BeerMapper beerMapper;
    private final BeerRepository beerRepository;

    @GetMapping("/{beerId}")
    public ResponseEntity getBeerById(@NotNull @PathVariable("beerId") UUID beerId) {
        return new ResponseEntity(beerMapper.beerToBeerDto(beerRepository.findById(beerId).get()), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getAllBeers() {

        Iterable<Beer> beers = beerRepository.findAll();

        //convert Iterable in Streameable object and transform to DTO
        List<BeerDto> beersDto = StreamSupport.stream(beers.spliterator(), false).map(beer -> {
            BeerDto beerDto = beerMapper.beerToBeerDto(beer);
            return beerDto;
        }).collect(Collectors.toList());

        return new ResponseEntity(beers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity saveNewBeer(@NotNull @Valid @RequestBody BeerDto beerDto) {
        Beer beer = beerRepository.save(beerMapper.beerDtoToBeer(beerDto));
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + beer.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity updateBeerById(@NotNull @PathVariable("beerId") UUID beerId,
                                         @NotNull @Valid @RequestBody BeerDto beerDto) {
        beerRepository.findById(beerId).ifPresent( beer -> {
            beer.setBeerName(beerDto.getBeerName());
            beer.setBeerStyle(beerDto.getBeerStyle().name());
            beer.setPrice(beerDto.getPrice());
            beer.setUpc(beerDto.getUpc());

            beerRepository.save(beer);
        });
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId) {
        beerRepository.deleteById(beerId);
    }

}
