package meetmeet.model.dao;

<<<<<<< HEAD
=======
<<<<<<< HEAD
import java.util.List;

import org.springframework.data.jpa.repository.Query;
=======
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.FriendRequest;

<<<<<<< HEAD

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long>{
	
=======
<<<<<<< HEAD
public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long> {
	@Query("SELECT fr.requestId.accountId, fr.requestedId.accountId FROM FriendRequest fr WHERE fr.requestId.accountId=:requestId")
	public abstract List<List<String>> findFriendRequestByRequestId(String requestId);

	@Query("SELECT fr.requestId.accountId, fr.requestedId.accountId FROM FriendRequest fr WHERE fr.requestedId.accountId=:requestedId")
	public abstract List<List<String>> findFriendRequestByRequestedId(String requestedId);

=======

public interface FriendRequestRepository extends CrudRepository<FriendRequest, Long>{
	
>>>>>>> 786581eb55fffb82c73722630ad380c640916168
>>>>>>> 9fe9137117702b39062f7012f6ae96b14e373f55
}
