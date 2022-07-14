package model.dao;

import org.springframework.data.repository.CrudRepository;

import model.entity.Account;


public interface AccountRepository extends CrudRepository<Account, String>{
	
}
