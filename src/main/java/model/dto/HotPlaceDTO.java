package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class HotPlaceDTO {

	private long hotPlaceId;
	private String placeName;
	private double xCoordinate;
	private double yCoordinate;
}
