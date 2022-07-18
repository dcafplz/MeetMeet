package meetmeet.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import meetmeet.model.dto.PreferenceDTO;
import meetmeet.model.entity.Account;
import meetmeet.model.entity.Preference;


public interface PreferenceRepository extends CrudRepository<Preference, Long>{

	Optional<Preference> deleteByAccountId(String accountId);

	List<Preference> findByAccountId(String accountId);
	
}
