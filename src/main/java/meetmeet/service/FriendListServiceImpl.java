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
import meetmeet.model.dao.FriendRequestRepository;
import meetmeet.model.dto.AccountDTO;
import meetmeet.model.dto.FriendListDTO;
import meetmeet.model.dto.FriendRequestDTO;
import meetmeet.model.entity.Account;
import meetmeet.model.entity.FriendList;
import meetmeet.model.entity.FriendRequest;

import org.modelmapper.ModelMapper;

@Service
public class FriendListServiceImpl implements FriendListService {
	@Autowired
	private FriendListRepository friendListRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private FriendRequestRepository friendRequestRepository;

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
		System.out.println("확인용");
		result = friendListRepository.findId2ById1AccountId(id1);
		System.out.println("확인용2");
		System.out.println(result);
		return result;
	}

	public String delete(String id1, String id2) {
		try {
			System.out.println("삭제시작");
			Optional<FriendList> result = friendListRepository.findMyFunction(id1, id2);
			Optional<FriendList> result2 = friendListRepository.findMyFunction(id2, id1);
			Integer list1 = null;
			Integer list2 = null;
			if (result.isPresent()) {
				list1 = result.get().getId();
			}
			if (result2.isPresent()) {
				list2 = result2.get().getId();
			}
			friendListRepository.deleteById(list1);
			friendListRepository.deleteById(list2);
	
			return "삭제성공";
		}catch(Exception e) {
			e.printStackTrace();
			return "삭제실패";
		}
	}

	public String post(String id1, String id2) {
		
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
//		FriendListDTO result = FriendListDTO.builder().id1(result3).id2(result4).build();
		Optional<FriendList> result5 = friendListRepository.findMyFunction(id1, id2);
		System.out.println("SDFSDFSDFSDFSDFSD");
		System.out.println(result5);
		if (result5.isPresent()) {
			//이미친구
			return "친구상태";
		}else {
			//친구아님
			FriendRequestDTO result = FriendRequestDTO.builder().requestId(result3).requestedId(result4).build();
			try {
				friendRequestRepository.save(modelMapper.map(result,FriendRequest.class));
				System.out.println("성공");		
			}catch(Exception e) {
				e.printStackTrace();
				System.out.println("캐치문확인용");
				return "친구신청이미보냄";
			}
		}return "친구요청성공";
	}

}
