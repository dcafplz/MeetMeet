package meetmeet.model.dao;

import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.Place;


public interface PlaceRepository extends CrudRepository<Place, Long>{
	
}
