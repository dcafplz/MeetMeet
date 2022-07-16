package meetmeet.model.dao;

import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.FriendRequest;


public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long>{
	
}
