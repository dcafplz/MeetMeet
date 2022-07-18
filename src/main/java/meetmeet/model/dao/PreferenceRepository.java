package meetmeet.model.dao;

import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.Preference;


public interface PreferenceRepository extends CrudRepository<Preference, Long>{
	
}
