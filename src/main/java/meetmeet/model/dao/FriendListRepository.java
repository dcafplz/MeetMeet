package meetmeet.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.FriendList;

public interface FriendListRepository extends CrudRepository<FriendList, Long> {

	@Query("SELECT fl.id2.accountId, fl.id2.nickName FROM FriendList fl WHERE fl.id1.accountId=:id1")
	public abstract List<List<String>> findId2ById1AccountId(String id1);

}
