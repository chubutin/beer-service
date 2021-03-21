package chubutin.springframework.beerservice.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Positive;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeerDto {

    @Null
    private UUID id;
//    @NotBlank
//    private Long version;


//    private OffsetDateTime createdDate;
//    private OffsetDateTime lastModifiedDate;
    @NotBlank
    private String beerName;
    @NotBlank
    private String beerStyle;
//    private BeerStyleEnum beerStyle;

    @Positive
    private Long upc;

//    private Integer quantityOnHand;
}
