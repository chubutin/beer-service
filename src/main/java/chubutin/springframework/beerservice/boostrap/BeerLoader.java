package chubutin.springframework.beerservice.boostrap;

import chubutin.springframework.beerservice.domain.Beer;
import chubutin.springframework.beerservice.repository.BeerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * This class implements CommandLineRunner in order to run when Spring App Context is loaded
 */
@Component
public class BeerLoader implements CommandLineRunner {

    private final BeerRepository beerRepository;

    public BeerLoader(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadBeerObjects();
    }

    private void loadBeerObjects() {
        if(beerRepository.count() == 0){
            beerRepository.save(Beer.builder()
                    .id(UUID.randomUUID())
                    .beerName("Strange LOVE")
                    .beerStyle("IPA")
                    .quantityToBrew(200)
                    .upc(11231313L)
                    .price(new BigDecimal("12.10"))
                    .build());

            beerRepository.save(Beer.builder()
                    .id(UUID.randomUUID())
                    .beerName("Strange HATE")
                    .beerStyle("PALE_ALE")
                    .quantityToBrew(200)
                    .upc(2231313L)
                    .price(new BigDecimal("2.10"))
                    .build());

            beerRepository.save(Beer.builder()
                    .id(UUID.randomUUID())
                    .beerName("Strange LIFE")
                    .beerStyle("STOUT")
                    .quantityToBrew(100)
                    .upc(1223112313L)
                    .price(new BigDecimal("1.10"))
                    .build());
        }

        System.out.println("Loaded Beers: " + beerRepository.count());
    }
}
