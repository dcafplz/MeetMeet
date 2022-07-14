package model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class MeetingParticipantDTO {
	
	private long meetingParticipantId;
	private long meetingId;
	private String participantId;
}
