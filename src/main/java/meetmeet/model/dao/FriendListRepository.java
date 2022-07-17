package meetmeet.model.dao;

import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.FriendList;


public interface FriendListRepository extends CrudRepository<FriendList, Long>{
	
}
