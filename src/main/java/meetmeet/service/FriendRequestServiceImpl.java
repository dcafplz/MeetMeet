package meetmeet.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
