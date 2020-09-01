package uk.gov.gsi.dwp.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Location {
	private String name;
	private double latitude;
	private double longitude;

}
