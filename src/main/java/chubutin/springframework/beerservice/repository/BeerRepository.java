package chubutin.springframework.beerservice.repository;

import chubutin.springframework.beerservice.domain.Beer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public interface BeerRepository extends PagingAndSortingRepository<Beer, UUID> {

}
