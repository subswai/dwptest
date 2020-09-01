package uk.gov.gsi.dwp.model;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class SearchCriteria {

    @NotNull(message = "Location name must be provided")
    @Getter
    @Setter
    private String location;

    @NotNull(message = "Distance must be provided")
    @Min(value = 0, message = "Distance must be greater than 0")
    Double distance;


}
