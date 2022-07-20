package meetmeet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import meetmeet.model.dao.PlaceRepository;
import meetmeet.model.dto.PlaceDTO;
import meetmeet.service.PlaceService;

@RestController
public class PlaceController {
	
	@Autowired
	private PlaceService placeService;
	
	@PostMapping("/place/getone")
	public PlaceDTO getOne(String id) throws Exception {
		return placeService.findByAccountId(id);
	}

}
