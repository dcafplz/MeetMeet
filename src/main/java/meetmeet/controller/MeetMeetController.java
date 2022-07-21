package meetmeet.controller;

import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MeetMeetController {

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
		return "friendlist"; // 에러페이지러변경!!
	}

	@GetMapping("/tofriendsearch")
	public String toFriendsSearch(HttpSession session) throws NoSuchAlgorithmException {
		return "friendsearch"; // 에러페이지러변경!!
	}

	@GetMapping("/tofriendrequest")
	public String toFriendsRequest(HttpSession session) throws NoSuchAlgorithmException {
		return "friendrequest"; // 에러페이지러변경!!
	}

	@GetMapping("/tosignup")
	public String toSignUp(HttpSession session) {
		return "signup";
	}

	@GetMapping("/tofindpw")
	public String toFindPw(HttpSession session) {
		return "findpw";
	}

	@GetMapping("nonSign")
	public String nonSign(HttpSession session) {
		return "redirect:/tohome";
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
	
	@GetMapping("/tomypagepw")
	public String toMyPagePw(HttpSession session) {
		if (session.getAttribute("accountId") != null) {
			return "mypagepw";
		} else {
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
	
}
