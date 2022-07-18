package meetmeet.service;

import java.util.ArrayList;
import java.util.List;

import meetmeet.model.dto.FriendListDTO;

public interface FriendListService {
	ArrayList<FriendListDTO> findAll();

	List<List<String>> findFriendListById1(String id1);
}
