package model.dao;

import org.springframework.data.repository.CrudRepository;

import model.entity.MeetingParticipant;


public interface MeetingParticipantRepository extends CrudRepository<MeetingParticipant, Long>{
	
}
