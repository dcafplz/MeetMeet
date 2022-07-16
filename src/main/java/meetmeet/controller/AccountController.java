package meetmeet.controller;

import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import meetmeet.model.dao.AccountRepository;
import meetmeet.model.dto.PwSecurity;
import meetmeet.model.entity.Account;


@SessionAttributes("account")
@Controller
public class AccountController {
	
	@Autowired
	private AccountRepository dao;

	@PostMapping("account/signup")
	public String signup(Account account) throws NoSuchAlgorithmException {
		Random random = new Random();
		account.setHashSalt(random.ints(48,123)
				  .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				  .limit(3)
				  .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
				  .toString());
		account.setPw(PwSecurity.hashing(account.getPw(), account.getHashSalt()));
		dao.save(account);
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
	
	@PostMapping("account/login")
	public String login(Model model, String accountId, String pw) throws NoSuchAlgorithmException {
		Optional<Account> account = dao.findById(accountId);
		try {
			if(PwSecurity.checkPw(account.get(), pw)) {
				model.addAttribute("account", account.get());
				return "redirect:../home.html";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:../login.html";
	}

}
