package meetmeet.service;

import java.util.ArrayList;
import java.util.List;

import meetmeet.model.dto.FriendListDTO;

public interface FriendListService {
	ArrayList<FriendListDTO> findAll();

	List<List<String>> findFriendListById1(String id1);
	
	public String delete(String id1, String id2);
	
	public String post(String id1, String id2);
}
