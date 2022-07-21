package meetmeet.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import meetmeet.model.dto.AccountDTO;
import meetmeet.model.dto.FriendListDTO;
import meetmeet.model.dto.FriendRequestDTO;
import meetmeet.service.FriendListServiceImpl;
import meetmeet.service.FriendRequestServiceImpl;

@RestController
@RequestMapping("friendrequest")
public class FriendRequestController {
	@Autowired
	private FriendRequestServiceImpl friendRequestService;

	@GetMapping("findFriendRequestByRequestId")
	public List<List<String>> findFriendRequestByRequestId(String requestId) {
		List<List<String>> result = null;
		result = friendRequestService.findFriendRequestByRequestId(requestId);
		System.out.println(result);
		System.out.println("요청확인용");
		return result;
	}

	@GetMapping("findFriendRequestByRequestedId")
	public List<List<String>> findFriendRequestByRequsetedId(String requestedId) {
		List<List<String>> result = null;
		System.out.println(requestedId);
		result = friendRequestService.findFriendRequestByRequestedId(requestedId);
		return result;
	}
	@DeleteMapping("accept")
	public String accept(String id, String id1, String id2) {
		System.out.println("친구요청수락--------------------------------");
		String result = null;
		result = friendRequestService.accept(id,id1,id2);
		return result;
	}
	@DeleteMapping("delete")
	public String delete(String id) {
		System.out.println("친구요청삭제--------------------------------");
		String result = null;
		result = friendRequestService.delete(id);
		return result;
	}

//	@PostMapping("post")
//	public String save(AccountDTO requestId, AccountDTO requestedId) {
//		String msg = "?";
//		requestId = AccountDTO.builder().accountId("1").pw("wer").hashSalt("12").pwQuestion("123").nickName("wqe")
//				.build();
//		requestedId = AccountDTO.builder().accountId("2").pw("wer").hashSalt("12").pwQuestion("123").nickName("wqe")
//				.build();
//		msg = friendRequestService.save(requestId, requestedId);
//		return msg;
//	}
}
