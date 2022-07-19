package meetmeet.model.dao;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.Account;


public interface AccountRepository extends CrudRepository<Account, String>{

	public List<Account> findByNickNameContaining(String searching);

	
	
}
