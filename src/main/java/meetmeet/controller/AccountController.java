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
		return "redirect:../login.html";
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
				return "home";
//		        return "redirect:../test.jsp";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:login.html";
	}
	@GetMapping("nonSign")
	public String nonSign(HttpSession session)  {
		return "home";
	}
	
	@ResponseBody
	@PostMapping("account/logincheck")
	public String loginCheck(HttpSession session) {

	    if (session != null) {
	        return "Y";
	    }

	    return "N";
	}
	
<<<<<<< HEAD
=======
	@ResponseBody
	@PostMapping("/getsession")
	public String[] getSession(HttpSession session) {
		return new String[] {session.getAttribute("accountId").toString(), session.getAttribute("nickName").toString()};
	}

	
>>>>>>> 46e5604c2a2df3d006296eeee5d272458de7a66a
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
		return "home.html";
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
		return "home.html";
	}
	
	@PostMapping("account/findpw")
	public String findPw(AccountDTO account, Model model) {
		
		Account accountEntity = dao.findById(account.getAccountId()).get();
		try {
			if(accountEntity.getPwQuestion().equals(account.getPwQuestion())) {
				accountEntity.setPw(PwSecurity.hashing(account.getPw(), accountEntity.getHashSalt()));
				dao.save(accountEntity);
				return "redirect:../login.html";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("msg","ID나 비밀번호 찾기 질문을 확인하세요");
		return "redirect:../findpw.html";
	}
	
	@GetMapping("account/changepreference")
	@Transactional
	public ModelAndView changePreference(@RequestParam(required = false) List<String> preference, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:../mypage.html");
		modelAndView.addObject("page",1);
		pDao.deleteByAccountId(session.getAttribute("accountId").toString());
		savePreference(session.getAttribute("accountId").toString(), preference);
		
		return modelAndView;
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
	
	
	@GetMapping("/mypage/{page}")
	public ModelAndView toMypage(HttpSession session, @PathVariable("page") String page) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:../mypage.html");
		if (session.getAttribute("accountId") == null) {
	        modelAndView.setViewName("redirect:../home.html");
		}
		modelAndView.addObject("page",page);
		return modelAndView;
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
	
	@GetMapping("/tofriends")
	public String toFriends(HttpSession session, String command) throws NoSuchAlgorithmException {
		System.out.println(command);
		if (command.equals("friendlist")) {
			System.out.println("friendlist확인용");
			return "forward:friends.html?command=friendlist";
		}else if (command.equals("addfriend")) {
			System.out.println("addfriend확인용");
			return "forward:friends.html?command=addfriend";
		}else {
			System.out.println("에러페이지로연결해주기!!");
		}
		return "friends"; //에러페이지러변경!!
	}

	@ResponseBody
	@PostMapping("/getsession")
	public String[] getSession(HttpSession session) {
		return new String[] {session.getAttribute("accountId").toString(), session.getAttribute("nickName").toString()};
	}
	
}
