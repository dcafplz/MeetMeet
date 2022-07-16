package meetmeet.model.dao;

import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.Account;


public interface AccountRepository extends CrudRepository<Account, String>{
	
}
