package meetmeet.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meetmeet.model.dao.AccountRepository;
import meetmeet.model.dao.FriendListRepository;
import meetmeet.model.dto.AccountDTO;
import meetmeet.model.dto.FriendListDTO;
import meetmeet.model.entity.Account;
import meetmeet.model.entity.FriendList;
import org.modelmapper.ModelMapper;

@Service
public class FriendListServiceImpl implements FriendListService {
	@Autowired
	private FriendListRepository friendListRepository;
	@Autowired
	private AccountRepository accountRepository;

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

	public void delete(Integer id) {
//		friendListRepository.deleteById1AccountIdAndId2AccountId(id1, id2);
		System.out.println("삭제시작");
		friendListRepository.deleteById(id);		
	}

	public void post(String id1, String id2) {
		
//		result.builder().id1(id1).
		Optional<Account> result1 = accountRepository.findById(id1);
		Optional<Account> result2 = accountRepository.findById(id2);
		System.out.println("진행중");
		AccountDTO result3 = null;
		AccountDTO result4 = null;
		if (result1.isPresent()) {
			result3 = modelMapper.map(result1.get(),AccountDTO.class);
		}
		if (result2.isPresent()) {
			result4 = modelMapper.map(result2.get(),AccountDTO.class);
		}
		System.out.println("진행중2");
		FriendListDTO result = FriendListDTO.builder().id1(result3).id2(result4).build();
		friendListRepository.save(modelMapper.map(result,FriendList.class));
		System.out.println("성공");

	}

}
