package meetmeet.model.dao;

import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.MeetingParticipant;


public interface MeetingParticipantRepository extends CrudRepository<MeetingParticipant, Long>{
	
}
