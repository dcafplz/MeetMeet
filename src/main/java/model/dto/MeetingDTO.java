package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MeetingDTO {
	
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
