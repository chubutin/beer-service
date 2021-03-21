package chubutin.springframework.beerservice.web.controller;

import chubutin.springframework.beerservice.web.model.BeerDto;
import chubutin.springframework.beerservice.services.BeerService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Validated
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/beer")
@RestController
public class BeerController {

    @Autowired
    BeerService beerService;

    @GetMapping("/{beerId}")
    public ResponseEntity getBeer(@NotNull @PathVariable("beerId") UUID beerId) {
        BeerDto beer = beerService.getBeer(beerId);
        return new ResponseEntity(beer, (beer != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @GetMapping
    public ResponseEntity getBeers() {
        return new ResponseEntity(beerService.getAllBeers(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity handlePost(@NotNull @Valid @RequestBody BeerDto beerDto) {
        BeerDto savedDto = beerService.saveBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedDto.getId().toString());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{beerId}")
    public ResponseEntity handlePut(@NotNull @PathVariable("beerId") UUID beerId,
                                    @NotNull @Valid @RequestBody BeerDto beerDto) {
        BeerDto savedDto = beerService.updateBeer(beerId, beerDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/beer/" + savedDto.getId().toString());
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleDelete(@PathVariable("beerId") UUID beerId) {
        beerService.deleteBeer(beerId);
    }

}
