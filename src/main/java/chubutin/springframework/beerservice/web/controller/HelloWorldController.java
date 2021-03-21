package chubutin.springframework.beerservice.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import chubutin.springframework.beerservice.services.BeerService;

@RequestMapping(value = "/v1/hello")
@RestController
public class HelloWorldController {

    @Autowired
    BeerService beerService;

    @GetMapping()
    public ResponseEntity getHello() {
        return new ResponseEntity("hello", HttpStatus.OK);
    }
}
