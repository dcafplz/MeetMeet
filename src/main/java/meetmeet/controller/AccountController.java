package meetmeet.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import meetmeet.model.dao.AccountRepository;
import meetmeet.model.dao.PreferenceRepository;
import meetmeet.model.dto.AccountDTO;
import meetmeet.model.dto.PreferenceDTO;
import meetmeet.model.dto.PwSecurity;
import meetmeet.model.entity.Account;
import meetmeet.model.entity.Preference;


@Controller
public class AccountController {
	
	@Autowired
	private AccountRepository dao;
	
	@Autowired
	private PreferenceRepository pDao;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	
	@PostMapping("account/signup")
	public String signup(AccountDTO account, @RequestParam(required = false) List<String> preference) throws NoSuchAlgorithmException {
		Random random = new Random();
		account.setHashSalt(random.ints(48,123)
				  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				  .limit(3)
				  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				  .toString());
		account.setPw(PwSecurity.hashing(account.getPw(), account.getHashSalt()));
		Account accountEntity = modelMapper.map(account, Account.class);
		dao.save(accountEntity);
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
	
	@PostMapping("account/logout")
	public String logout(HttpSession session) {

	    if (session != null) {
	        session.invalidate();   // 세션 날림
	    }

	    return "home.html";
	}

//	@PostMapping("account/findpw")
//	public ModelAndView findPw(String pwQuestion, String accountId) {
//		ModelAndView modelAndView = new ModelAndView();
//		
//		Optional<Account> account = dao.findById(accountId);
//		try {
//			if(account.get().getPwQuestion().equals(pwQuestion)) {
//				modelAndView.setViewName("home.html");
//				
//				return modelAndView;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		modelAndView.setViewName("mypage.html?command=mypw");
//		return modelAndView;
//	}
	
	@PostMapping("account/changepreference")
	public ModelAndView changePreference(String accountId, @RequestParam(required = false) List<String> preference) throws NoSuchAlgorithmException {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("mypage.html?command=meettype");
		
		pDao.deleteByAccountId(accountId);
		savePreference(accountId, preference);
		
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
	public List<String> getPreference(String accountId) {
		List<Preference> dto = pDao.findByAccountId(accountId);
		List<String> r = new ArrayList<>();
		if (dto != null) {
			for(Preference p: dto) {
				r.add(p.getCategory());
			}
		}

		return r;
	}
	
	@GetMapping("/tohome")
	public String toHome(HttpSession session) throws NoSuchAlgorithmException {
		return "home";
	}
	
	@GetMapping("/tologin")
	public String toLogin(HttpSession session) throws NoSuchAlgorithmException {
		return "login";
	}
	
	@GetMapping("/toabout")
	public String toAbout(HttpSession session) throws NoSuchAlgorithmException {
		return "about";
	}

	
}
