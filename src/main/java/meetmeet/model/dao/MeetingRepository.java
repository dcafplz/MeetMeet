package meetmeet.model.dao;

import org.springframework.data.repository.CrudRepository;
<<<<<<< HEAD

import meetmeet.model.entity.Meeting;


=======
import org.springframework.stereotype.Repository;

import meetmeet.model.entity.Meeting;
<<<<<<< HEAD

=======
>>>>>>> 786581eb55fffb82c73722630ad380c640916168

@Repository
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
public interface MeetingRepository extends CrudRepository<Meeting, Long>{
	
}
