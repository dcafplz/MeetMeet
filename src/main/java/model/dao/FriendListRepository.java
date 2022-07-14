package model.dao;

import org.springframework.data.repository.CrudRepository;

import model.entity.FriendList;


public interface FriendListRepository extends CrudRepository<FriendList, Long>{
	
}
