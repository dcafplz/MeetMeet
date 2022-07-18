package meetmeet.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meetmeet.model.dao.FriendListRepository;
import meetmeet.model.dto.FriendListDTO;
import meetmeet.model.entity.Account;
import meetmeet.model.entity.FriendList;
import org.modelmapper.ModelMapper;

@Service
public class FriendListServiceImpl implements FriendListService {
	@Autowired
	private FriendListRepository friendListRepository;

	private static ModelMapper modelMapper = new ModelMapper();

	public ArrayList<FriendListDTO> findAll() {
		Iterable<FriendList> result = friendListRepository.findAll();

		ArrayList<FriendListDTO> result2 = new ArrayList<FriendListDTO>();

		for (FriendList friend : result) {
			FriendListDTO friend2 = modelMapper.map(friend, FriendListDTO.class);
			result2.add(friend2);
			System.out.println(friend2);
		}
		return result2;
	}

	public List<List<String>> findFriendListById1(String id1) {
		List<List<String>> result = null;
		result = friendListRepository.findId2ById1AccountId(id1);
		System.out.println(result);
		return result;
	}

}
