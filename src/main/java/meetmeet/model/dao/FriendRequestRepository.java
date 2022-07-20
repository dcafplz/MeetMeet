package meetmeet.model.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import meetmeet.model.entity.FriendRequest;
@Repository
public interface FriendRequestRepository extends CrudRepository<FriendRequest, Integer> {
	@Query("SELECT fr.requestId.accountId, fr.requestedId.accountId FROM FriendRequest fr WHERE fr.requestId.accountId=:requestId")
	public abstract List<List<String>> findFriendRequestByRequestId(String requestId);

	@Query("SELECT fr.requestId.accountId, fr.requestedId.accountId FROM FriendRequest fr WHERE fr.requestedId.accountId=:requestedId")
	public abstract List<List<String>> findFriendRequestByRequestedId(String requestedId);


}
