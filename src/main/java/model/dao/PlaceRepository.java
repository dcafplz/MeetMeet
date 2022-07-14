package model.dao;

import org.springframework.data.repository.CrudRepository;

import model.entity.Place;


public interface PlaceRepository extends CrudRepository<Place, Long>{
	
}
