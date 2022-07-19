package meetmeet.model.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.MeetingParticipant;


public interface MeetingParticipantRepository extends CrudRepository<MeetingParticipant, Long>{
	
	MeetingParticipant findMeetingParticipantByMeetingIdAndParticipantId(Long meetingId, String participantId);
	
	List<MeetingParticipant> findMeetingParticipantByParticipantId(String participantId);
	
}
