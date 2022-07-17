package meetmeet.model.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class HotPlace {

	@Id
	private long hotPlaceId;
	private String placeName;
	private double xCoordinate;
	private double yCoordinate;
}
