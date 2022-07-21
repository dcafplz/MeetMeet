package meetmeet.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.ApiOperation;
import meetmeet.model.dao.FriendListRepository;
import meetmeet.model.dto.FriendListDTO;
import meetmeet.model.entity.Account;
import meetmeet.model.entity.FriendList;
import meetmeet.service.FriendListServiceImpl;

@RestController
@RequestMapping("friendlist")
public class FriendListController {
	@Autowired
	private FriendListServiceImpl friendListService;

	@GetMapping("findall")
	@ApiOperation(value="밸류")
	public ArrayList<FriendListDTO> findAll() {
		System.out.println("friendlist/findAll() 실행----------------------------------");
		ArrayList<FriendListDTO> result = null;
		result = friendListService.findAll();
		return result;
	}

	@GetMapping("findFriendListById1")
	public List<List<String>> findFriendListById1(String id1) {
		System.out.println("friendlist/findFriendListById1() 실행----------------------------------");
		List<List<String>> result = null;
		result = friendListService.findFriendListById1(id1);
		return result;
	}
	
	@DeleteMapping("delete")
	public String delete(String id1,String id2) {
		System.out.println("friendlist/delete() 실행----------------------------------");
		String result = null;
		result = friendListService.delete(id1,id2);
		return result;
	}
	
	@PostMapping("post")
	public String post(String id1, String id2) {
		System.out.println("friendlist/post() 실행----------------------------");
		String result = null;
		result = friendListService.post(id1,id2);
		if (result.equals("친구상태")) {
			return "이미 친구 목록에 존재합니다";
		}else if(result.equals("친구신청이미보냄")) {
			return "이미 친구신청을 보냈습니다";
		}return "친구요청에 성공했습니다";

	}

}
