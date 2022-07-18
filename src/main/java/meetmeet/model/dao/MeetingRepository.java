package meetmeet.model.dao;

import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.Meeting;


public interface MeetingRepository extends CrudRepository<Meeting, Long>{
	
}
