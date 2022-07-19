package meetmeet.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import meetmeet.model.dto.MeetingDTO;
import meetmeet.model.dto.MeetingParticipantDTO;
import meetmeet.model.entity.Meeting;
import meetmeet.model.entity.MeetingParticipant;
import meetmeet.service.MeetingParticipantService;

@RestController
public class MeetingParticipantController {
	
	@Autowired
	private MeetingParticipantService meetingParticipantService;

	@PostMapping("/meetparticipate")
	public void meetParticipate(MeetingParticipantDTO mp, HttpServletRequest req) throws Exception {
		System.out.println(mp.getMeetingId());
		System.out.println(req.getSession().getAttribute("accountId"));
		mp.setParticipantId((String) req.getSession().getAttribute("accountId"));
		meetingParticipantService.meetParticipate(mp);
	}
	
	@PostMapping("/check/meetparticipate")
	public boolean checkMeetParticipate(MeetingParticipantDTO mp, HttpServletRequest req) throws Exception {
		MeetingParticipantDTO result = meetingParticipantService.
			searchByParticipantAndMeet(mp.getMeetingId(),
					(String) req.getSession().getAttribute("accountId"));
		if(result != null) {
			return true;
		}else {
			return false;
		}
	}
	
	@PostMapping("/cancel/meetparticipate")
	public boolean cancelMeetParticipate(MeetingParticipantDTO mp, HttpServletRequest req) throws Exception {
		return meetingParticipantService.
				meetParticipationDelete(mp.getMeetingId(),
						(String) req.getSession().getAttribute("accountId"));
	}
	
	@GetMapping("/meetparticipateall")
	public List<MeetingParticipant> getMeetParticipateAll(HttpServletRequest req) throws Exception {
		System.out.println(req.getSession().getAttribute("accountId"));
		return meetingParticipantService.
				searchByParticipant((String) req.getSession().getAttribute("accountId"));
	}

}
