package meetmeet.model.dao;

<<<<<<< HEAD
=======
<<<<<<< HEAD
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.Account;
import meetmeet.model.entity.FriendList;

public interface FriendListRepository extends CrudRepository<FriendList, Long> {

	@Query("SELECT fl.id2.accountId, fl.id2.nickName FROM FriendList fl WHERE fl.id1.accountId=:id1")
	public abstract List<List<String>> findId2ById1AccountId(String id1);

=======
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.FriendList;


public interface FriendListRepository extends CrudRepository<FriendList, Long>{
	
<<<<<<< HEAD
=======
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
}
