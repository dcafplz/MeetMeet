package model.dao;

import org.springframework.data.repository.CrudRepository;

import model.entity.Meeting;


public interface MeetingRepository extends CrudRepository<Meeting, Long>{
	
}
