package meetmeet.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import meetmeet.model.dao.AccountRepository;
import meetmeet.model.dao.PlaceRepository;
import meetmeet.model.dao.PreferenceRepository;
import meetmeet.model.dto.AccountDTO;
import meetmeet.model.dto.PlaceDTO;
import meetmeet.model.dto.PreferenceDTO;
import meetmeet.model.dto.PwSecurity;
import meetmeet.model.entity.Account;
import meetmeet.model.entity.Place;
import meetmeet.model.entity.Preference;


@Controller
public class AccountController {
	
	@Autowired
	private AccountRepository dao;
	
	@Autowired
	private PreferenceRepository pDao;

	@Autowired
	private PlaceRepository plDao;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	
	@PostMapping("account/signup")
	public String signup(AccountDTO account, @RequestParam(required = false) List<String> preference, PlaceDTO place) throws NoSuchAlgorithmException {
		Random random = new Random();
		account.setHashSalt(random.ints(48,123)
				  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				  .limit(3)
				  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				  .toString());
		account.setPw(PwSecurity.hashing(account.getPw(), account.getHashSalt()));
		Account accountEntity = modelMapper.map(account, Account.class);
		dao.save(accountEntity);
		Place placeEntity = modelMapper.map(place, Place.class);
		plDao.save(placeEntity);
		if (preference != null) {savePreference(account.getAccountId(), preference);}
		return "redirect:/tologin";
	}
	
	@ResponseBody
	@GetMapping("account/check")
	public String check(String accountId) {
		System.out.println(accountId);
		Optional<Account> account = dao.findById(accountId);
		System.out.println(account);
		if (account.isEmpty()) {
			return "N";
		}
		return "Y";
	}
	
	@PostMapping("login")
	public String login(String accountId, String pw, HttpSession session) throws NoSuchAlgorithmException {
			
		Optional<Account> account = dao.findById(accountId);
		try {
			if(PwSecurity.checkPw(account.get(), pw)) {
		        session.setAttribute("accountId", account.get().getAccountId());
		        session.setAttribute("nickName", account.get().getNickName());
		        return "redirect:/tohome";
//		        return "redirect:../test.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:/tologin";
	}
	@GetMapping("nonSign")
	public String nonSign(HttpSession session)  {
		return "redirect:/tohome";

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
	        session.invalidate();   // 세션 날림
	    }

	    return "redirect:/tohome";

	}

	@PostMapping("account/changenickname")
	public String changeNickName(HttpSession session, String nickName) {
		Account account = dao.findById(session.getAttribute("accountId").toString()).get();
		account.setNickName(nickName);
		session.setAttribute("nickName", nickName);
		dao.save(account);
		return "redirect:/tohome";
	}
	
	@PostMapping("account/changepw")
	public String changePw(HttpSession session, String pw) {
		Account account = dao.findById(session.getAttribute("accountId").toString()).get();
		try {
			account.setPw(PwSecurity.hashing(pw, account.getHashSalt()));
			dao.save(account);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		logout(session);
		return "redirect:/tohome";
	}
	
	@PostMapping("account/findpw")
	public String findPw(AccountDTO account, Model model) {
		
		Account accountEntity = dao.findById(account.getAccountId()).get();
		try {
			if(accountEntity.getPwQuestion().equals(account.getPwQuestion())) {
				accountEntity.setPw(PwSecurity.hashing(account.getPw(), accountEntity.getHashSalt()));
				dao.save(accountEntity);
				return "redirect:/tologin";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("msg","ID나 비밀번호 찾기 질문을 확인하세요");
		return "redirect:/tofindpw";
	}
	
	@GetMapping("account/changepreference")
	@Transactional
	public String changePreference(@RequestParam(required = false) List<String> preference, HttpSession session) {
		pDao.deleteByAccountId(session.getAttribute("accountId").toString());
		savePreference(session.getAttribute("accountId").toString(), preference);
		
		return "redirect:/tohome";
	}
	
	public void savePreference(String accountid, List<String> preference) {
		for(String s: preference) {
			PreferenceDTO dto = PreferenceDTO.builder().category(s).accountId(accountid).build();
			Preference entity = modelMapper.map(dto, Preference.class);
			pDao.save(entity);
		}
	}
	
	@ResponseBody
	@GetMapping("account/getpreference")
	public List<String> getPreference(HttpSession session) {
		List<Preference> dto = pDao.findByAccountId(session.getAttribute("accountId").toString());
		List<String> r = new ArrayList<>();
		if (dto != null) {
			for(Preference p: dto) {
				r.add(p.getCategory());
			}
		}

		return r;
	}
	@ResponseBody
	@GetMapping("searchUser")
	public List<List<String>> searchUser(String searching){
		System.out.println(searching);
		List<Account> result = new ArrayList<Account>();
		result= dao.findByNickNameContaining(searching);
		List<List<String>> result2 = new ArrayList<List<String>>();
		
		for (Account i : result) {
			AccountDTO j = new AccountDTO();
			j = modelMapper.map(i,AccountDTO.class);
			List<String> temp = new ArrayList<String>();
			temp.add(j.getAccountId());
			temp.add(j.getNickName());
			result2.add(temp);
		};
		System.out.println(result2);
		System.out.println("성공확인용");
		return result2;
	}
	
	
	@GetMapping("/tomypagepre")
	public String toMyPagePre(HttpSession session) {
		if (session.getAttribute("accountId") != null) {
			return "mypagepre";
		}else {
			session.invalidate();
			return "redirect:/tohome";
		}
	}
	
	@GetMapping("/tomypagenic")
	public String toMyPageNic(HttpSession session) {
		if (session.getAttribute("accountId") != null) {
			return "mypagenic";
		}else {
			session.invalidate();
			return "redirect:/tohome";
		}
	}
	
	@GetMapping("/tomypagepw")
	public String toMyPagePw(HttpSession session) {
		if (session.getAttribute("accountId") != null) {
			return "mypagepw";
		}else {
			session.invalidate();
			return "redirect:/tohome";
		}
	}
	
	@GetMapping("/tohome")
	public String toHome(HttpSession session) {
		return "home";
	}
	
	@GetMapping("/tologin")
	public String toLogin(HttpSession session) {
		return "login";
	}
	
	@GetMapping("/toabout")
	public String toAbout(HttpSession session) {
		return "about";
	}
	
	@GetMapping("/tofriendlist")
	public String toFriendList(HttpSession session) throws NoSuchAlgorithmException {
		return "friendlist"; //에러페이지러변경!!
	}
	@GetMapping("/tofriendsearch")
	public String toFriendsSearch(HttpSession session) throws NoSuchAlgorithmException {
		return "friendsearch"; //에러페이지러변경!!
	}

	@ResponseBody
	@PostMapping("/getsession")
	public String[] getSession(HttpSession session) {
		return new String[] {session.getAttribute("accountId").toString(), session.getAttribute("nickName").toString()};
	}
	
	@GetMapping("/tosignup")
	public String toSignUp(HttpSession session) {
		return "signup";
	}

	@GetMapping("/tofindpw")
	public String toFindPw(HttpSession session) {
		return "findpw";
	}
}
