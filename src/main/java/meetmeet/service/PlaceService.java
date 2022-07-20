package meetmeet.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meetmeet.model.dao.PlaceRepository;
import meetmeet.model.dto.PlaceDTO;

@Service 
public class PlaceService {
	
	@Autowired
	private PlaceRepository placeRepository;
	
	private ModelMapper modelMapper = new ModelMapper();

	public PlaceDTO findByAccountId(String id) {
		return modelMapper.map(placeRepository.findPlaceByAccountId(id), PlaceDTO.class);
	}

}
