package model.entity;

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
public class Meeting {
	
	@Id
	private long meetingId;
	private java.util.Date meetingStartDate;
	private java.util.Date meetingEndDate;
	private String meetingName;
	private String meetingDetail;
	private String meetingPlace;
	private String meetingPlaceLat;
	private String meetingPlaceLng;
	private String master_id;
	private String category;
	private int maxParticipant;
}
