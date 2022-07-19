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
	public ArrayList<FriendListDTO> findAll() {
		ArrayList<FriendListDTO> result = null;
		result = friendListService.findAll();
		return result;
	}

	@GetMapping("findFriendListById1")
	public List<List<String>> findFriendListById1(String id1) {
		List<List<String>> result = null;
		result = friendListService.findFriendListById1(id1);
		return result;
	}
	
	@DeleteMapping("delete")
	public void delete(Integer id) {
		System.out.println(id);
		friendListService.delete(id);
	}
	
	@PostMapping("post")
	public void post(String id1, String id2) {
		System.out.println(id1);
		System.out.println(id2);
		friendListService.post(id1,id2);
	}

}
