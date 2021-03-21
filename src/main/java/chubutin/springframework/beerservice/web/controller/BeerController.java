package chubutin.springframework.beerservice.web.controller;

import chubutin.springframework.beerservice.web.model.BeerDto;
import chubutin.springframework.beerservice.web.model.BeerPagedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import chubutin.springframework.beerservice.web.service.BeerService;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.ValidationException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Validated
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

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List> validationErrorHandler(ConstraintViolationException e){
        List<String> errors = new ArrayList<>(e.getConstraintViolations().size());
        e.getConstraintViolations().forEach(constraintViolation -> {
            errors.add(constraintViolation.getPropertyPath() + " : " + constraintViolation.getMessage());
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List> validationErrorHandler(MethodArgumentNotValidException e) {


        List<String> errores = new ArrayList<>();

        List<FieldError> fieldErrors = e.getBindingResult().getAllErrors().stream()
                .map(FieldError.class::cast)
                .collect(Collectors.toList());

        fieldErrors.forEach(fieldError -> {

            errores.add( String.format("Bad Request %s : %s : Rejected value : ---> %s"
                    ,fieldError.getField()
                    ,fieldError.getDefaultMessage()
                    ,fieldError.getRejectedValue()));
        });

        return new ResponseEntity<>(errores, HttpStatus.BAD_REQUEST);
    }

}
