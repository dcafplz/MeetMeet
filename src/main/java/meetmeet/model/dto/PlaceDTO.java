package meetmeet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class PlaceDTO {

	private long placeId;
	private String accountId;
	private String placeName;
	private double lat;
	private double lng;
}
