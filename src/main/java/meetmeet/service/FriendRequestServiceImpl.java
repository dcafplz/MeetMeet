package meetmeet.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.qos.logback.core.rolling.helper.IntegerTokenConverter;
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
public class FriendRequestServiceImpl implements FriendRequestService {
	@Autowired
	private FriendRequestRepository friendRequestRepository;
	@Autowired
	private FriendListRepository friendListRepository;
	@Autowired
	private AccountRepository accountRepository;

	private static ModelMapper modelMapper = new ModelMapper();

	public List<List<String>> findFriendRequestByRequestId(String requestId) {
		List<List<String>> result = null;
		result = friendRequestRepository.findFriendRequestByRequestId(requestId);
		System.out.println(result);
		return result;
	}

	public List<List<String>> findFriendRequestByRequestedId(String requestedId) {
		List<List<String>> result = null;
		result = friendRequestRepository.findFriendRequestByRequestedId(requestedId);
		System.out.println(result);
		return result;
	}

	public String accept(String id, String id1, String id2) {
		try {
			Integer a = null;
			a = Integer.parseInt(id);
			friendRequestRepository.deleteById(a);
			
			Optional<Account> result1 = accountRepository.findById(id1);
			Optional<Account> result2 = accountRepository.findById(id2);
			AccountDTO result3 = null;
			AccountDTO result4 = null;
			if (result1.isPresent()) {
				result3 = modelMapper.map(result1.get(),AccountDTO.class);
			}
			if (result2.isPresent()) {
				result4 = modelMapper.map(result2.get(),AccountDTO.class);
			}
			System.out.println("진행상황확인용");
			FriendListDTO result = FriendListDTO.builder().id1(result3).id2(result4).build();
			friendListRepository.save(modelMapper.map(result, FriendList.class));
			System.out.println("성공");
			FriendListDTO resultt = FriendListDTO.builder().id2(result3).id1(result4).build();
			friendListRepository.save(modelMapper.map(resultt, FriendList.class));
			System.out.println("성공");
			return "수락요청";
			
		}catch(Exception e) {
			e.printStackTrace();
			return "수락하는 과정 중 오류가 발생했습니다";
		}
	}

	public String delete(String id) {
		try {
			Integer id2 = null;
			id2 = Integer.parseInt(id);
			friendRequestRepository.deleteById(id2);
			return "수락삭제완료";
		} catch (Exception e) {
			e.printStackTrace();
			return "수락삭제하는 과정 중 오류가 발생했습니다";
		}
	}

//	public String save(AccountDTO requestId, AccountDTO requestedId) {
//		System.out.println(requestId);
//		try {
//			Account account1 = modelMapper.map(requestId, Account.class);
//			Account account2 = modelMapper.map(requestedId, Account.class);
//			System.out.println(account1);
////			FriendRequest friendRequest = new FriendRequest();
////			friendRequest.setRequestId(account1);
////			friendRequest.setRequestedId(account2);
//			FriendRequest friendRequest = modelMapper.map(requestedId, FriendRequest.class);
//			System.out.println(friendRequest);
//			friendRequestRepository.save(friendRequest);
//		} catch (Exception e) {
//			e.printStackTrace();
//			return "친구 신청에 실패했습니다";
//		}
//		return "친구 신청을 보냈습니다";
//	}

}
