package meetmeet.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import meetmeet.model.dto.AccountDTO;
import meetmeet.model.dto.PlaceDTO;
import meetmeet.model.entity.Account;
import meetmeet.model.entity.Preference;
import meetmeet.service.AccountService;
import meetmeet.service.FileSaveService;
import meetmeet.service.PlaceService;
import meetmeet.service.PreferenceService;
import meetmeet.service.PwSecurityService;

@Controller
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private PreferenceService preferenceService;

	@Autowired
	private PlaceService placeService;

	@Autowired
	private FileSaveService fileSaveService;

	@PostMapping("account/signup")
	public String signup(AccountDTO account, @RequestParam(required = false) List<String> preference, PlaceDTO place,
			@RequestParam(value = "file", required = false) MultipartFile file) throws NoSuchAlgorithmException, IOException {
		
		accountService.save(account);
		placeService.save(place);
		fileSaveService.saveFile(file, "/"+ account.getAccountId() + "_profile", "/default_profile.jpg");
		if(preference != null) { preferenceService.save(account.getAccountId(), preference);}

		return "redirect:/tologin";
	}

	@ResponseBody
	@GetMapping("account/check")
	public String check(String accountId) {
		if (accountService.findById(accountId).isEmpty()) {
			return "N";
		}
		return "Y";
	}

	@PostMapping("login")
	public String login(String accountId, String pw, HttpSession session) throws NoSuchAlgorithmException {

		Optional<Account> account = accountService.findById(accountId);
		try {
			if (PwSecurityService.checkPw(account.get(), pw)) {
				session.setAttribute("accountId", account.get().getAccountId());
				session.setAttribute("nickName", account.get().getNickName());
				return "redirect:/tohome";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/tologin";
	}

	@ResponseBody
	@PostMapping("account/logincheck")
	public String loginCheck(HttpSession session) {

		if (session != null) {
			return "Y";
		}

		return "N";
	}

	@GetMapping("account/logout")
	public String logout(HttpSession session) {

		if (session != null) {
			session.invalidate(); // 세션 날림
		}

		return "redirect:/tohome";

	}

	@PostMapping("account/changenickname")
	public String changeNickName(HttpSession session, String nickName) {
		
		accountService.changeNickName(session.getAttribute("accountId").toString(), nickName);
		session.setAttribute("nickName", nickName);

		return "redirect:/tohome";
	}

	@PostMapping("account/changepw")
	public String changePw(HttpSession session, String pw) throws NoSuchAlgorithmException {
		
		accountService.changePw(session.getAttribute("accountId").toString(), pw);
		logout(session);
		
		return "redirect:/tohome";
	}

	@PostMapping("account/findpw")
	public String findPw(AccountDTO account) throws NoSuchAlgorithmException {
		
		return accountService.findPw(account);

	}

	@GetMapping("account/changepreference")
	@Transactional
	public String changePreference(@RequestParam(required = false) List<String> preference, HttpSession session) {
		preferenceService.deleteByAccountId(session.getAttribute("accountId").toString());
		preferenceService.save(session.getAttribute("accountId").toString(), preference);
		

		return "redirect:/tohome";
	}



	@ResponseBody
	@GetMapping("account/getpreference")
	public List<String> getPreference(HttpSession session) {
		List<Preference> dto = preferenceService.findByAccountId(session.getAttribute("accountId").toString());
		
		List<String> r = new ArrayList<>();
		if (dto != null) {
			for (Preference p : dto) {
				r.add(p.getCategory());
			}
		}

		return r;
	}

	private ModelMapper modelMapper = new ModelMapper();
	
	@ResponseBody
	@GetMapping("searchUser")
	public List<List<String>> searchUser(HttpSession session, String searching, String id) {
		System.out.println(searching);

		id = session.getAttribute("accountId").toString();
		System.out.println(id);
		List<Account> result = new ArrayList<Account>();
		result = accountService.findByNickNameContainingAndAccountIdNot(searching, id);
		List<List<String>> result2 = new ArrayList<List<String>>();

		for (Account i : result) {
			AccountDTO j = new AccountDTO();
			j = modelMapper.map(i, AccountDTO.class);
			List<String> temp = new ArrayList<String>();
			temp.add(j.getAccountId());
			temp.add(j.getNickName());
			result2.add(temp);
		}
		;
		System.out.println(result2);
		System.out.println("성공확인용");
		return result2;
	}

	@ResponseBody
	@PostMapping("/getsession")
	public String[] getSession(HttpSession session) {
		return new String[] { session.getAttribute("accountId").toString(),
				session.getAttribute("nickName").toString() };
	}

}
