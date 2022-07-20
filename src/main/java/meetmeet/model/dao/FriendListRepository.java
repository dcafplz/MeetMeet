package meetmeet.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import meetmeet.model.entity.Account;
import meetmeet.model.entity.FriendList;
@Repository
public interface FriendListRepository extends CrudRepository<FriendList, Integer> {

	@Query("SELECT fl.id, fl.id2.accountId, fl.id2.nickName FROM FriendList fl WHERE fl.id1.accountId=:id1")
	public abstract List<List<String>> findId2ById1AccountId(String id1);

	public abstract void deleteById1AccountIdAndId2AccountId(String id1, String id2);

	



}
