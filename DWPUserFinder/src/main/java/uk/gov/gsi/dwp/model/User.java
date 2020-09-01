package uk.gov.gsi.dwp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@ToString
@EqualsAndHashCode
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class User {
    private int id;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private String email;
    @JsonProperty("ip_address")
    private String ipAddress;
    private double latitude;
    private double longitude;
    private String city;
    @JsonProperty("distance_from_city")
    private double distanceFromCity;

}
