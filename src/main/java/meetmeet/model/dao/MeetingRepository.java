package meetmeet.model.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import meetmeet.model.entity.Meeting;

@Repository
public interface MeetingRepository extends CrudRepository<Meeting, Long>{
	
}
