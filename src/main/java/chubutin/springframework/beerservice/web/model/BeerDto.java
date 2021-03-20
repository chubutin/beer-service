package chubutin.springframework.beerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {
    private UUID id;
    private Long version;

    private OffsetDateTime createdDate;
    private OffsetDateTime lastModifiedDate;

    private String beerName;
    private BeerStyleEnum beerStyle;

    private Long upc;

    private Integer quantityOnHand;
}
