package meetmeet.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meetmeet.model.dao.PreferenceRepository;
import meetmeet.model.dto.PreferenceDTO;
import meetmeet.model.entity.Preference;

@Service
public class PreferenceService {
	
	@Autowired
	private PreferenceRepository preferenceRepository;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	public void save(String accountid, List<String> preference) {
		for (String s : preference) {
			PreferenceDTO dto = PreferenceDTO.builder().category(s).accountId(accountid).build();
			Preference entity = modelMapper.map(dto, Preference.class);
			preferenceRepository.save(entity);
		}
	}

	public void deleteByAccountId(String string) {
		// TODO Auto-generated method stub
		
	}

	public List<Preference> findByAccountId(String accountId) {
		return preferenceRepository.findByAccountId(accountId);
	}

}
