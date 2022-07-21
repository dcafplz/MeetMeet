package meetmeet.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import meetmeet.model.dto.AccountDTO;
import meetmeet.model.dto.MeetingDTO;
import meetmeet.model.dto.MeetingParticipantDTO;
import meetmeet.model.entity.Account;
import meetmeet.model.entity.Meeting;
import meetmeet.model.entity.MeetingParticipant;
import meetmeet.service.MeetingParticipantService;
import meetmeet.service.MeetingService;

@RestController
public class MeetingController {

	@Autowired
	private MeetingService meetingService;
	
	@Autowired
	private MeetingParticipantService meetingParticipantService;

	@PostMapping("/create-meet")
	public ModelAndView meetCreatePage(Model model, HttpSession session, HttpServletRequest req) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(req.getSession().getAttribute("accountId") != null) {
			modelAndView.setViewName("createmeet.html");
		}else {
			modelAndView.setViewName("redirect:/tohome");
		}
		return modelAndView;
	}
	
	@PostMapping("/meetmeet/create-meet")
	public ModelAndView meetCreate(MeetingDTO meeting, Model model, MultipartFile file, HttpServletRequest req) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(req.getSession().getAttribute("accountId") != null) {
			
			String accountId = (String) req.getSession().getAttribute("accountId");
			
			meeting.setMaster_id(accountId);
			Long id = meetingService.meetCreate(meeting, file);
			
			MeetingParticipantDTO mp = new MeetingParticipantDTO();
			mp.setMeetingId(id);
			mp.setParticipantId(accountId);
			meetingParticipantService.meetParticipate(mp);
			
			modelAndView.setViewName("redirect:../meetmeet/detail?meetingId=" + id);
			modelAndView.addObject("meeting", meetingService.meetView(id));
			
		}else {
			modelAndView.setViewName("redirect:/tohome");
		}

		return modelAndView;
	}


	@GetMapping("/meetmeet/detail")
	public ModelAndView meetView(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("meetdetail.html");
		modelAndView.addObject("meeting", meetingService.meetView(meetingId));

		return modelAndView;
	}
	
	@GetMapping("/meetmeet/modify/{meetingId}")
    public ModelAndView meetModify(@PathVariable("meetingId") Long meetingId, Model model){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("meetmodify.html");
		modelAndView.addObject("meeting", meetingService.meetView(meetingId));
        return modelAndView;
    }

    @PostMapping("/meetmeet/update/{meetingId}")
    public ModelAndView meetUpdate(@PathVariable("meetingId") Long meetingId, MeetingDTO meeting, Model model, MultipartFile file) throws Exception{

        MeetingDTO meetingTemp = meetingService.meetView(meetingId);
        meetingTemp.setMeetingName(meeting.getMeetingName());
        meetingTemp.setMeetingPlace(meeting.getMeetingPlace());
        meetingTemp.setMeetingPlaceLat(meeting.getMeetingPlaceLat());
        meetingTemp.setMeetingPlaceLng(meeting.getMeetingPlaceLng());
        meetingTemp.setMeetingDetail(meeting.getMeetingDetail());
        meetingTemp.setCategory(meeting.getCategory());
        meetingTemp.setMaxParticipant(meeting.getMaxParticipant());
        meetingTemp.setMeetingStartDate(meeting.getMeetingStartDate());
        meetingTemp.setMeetingEndDate(meeting.getMeetingEndDate());
        
        ModelAndView modelAndView = new ModelAndView();
        System.out.println(file);
        Long id = meetingService.meetCreate(meetingTemp, file);
        modelAndView.setViewName("redirect:../detail?meetingId=" + id);
		modelAndView.addObject("meeting", meetingService.meetView(id));
        return modelAndView;
        
    }

	@GetMapping("/meetmeet/delete")
	public ModelAndView meetDelete(Long meetingId, HttpServletRequest req) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("redirect:/tohome");
		if(req.getSession().getAttribute("accountId").equals(getOne(meetingId).getMaster_id())) {
			meetingService.meetDelete(meetingId);
			meetingParticipantService.
			meetParticipationDelete(meetingId,
					(String) req.getSession().getAttribute("accountId"));
		}
		
		return modelAndView;
	}
	
	@GetMapping("/getall")
	public Iterable<Meeting> getAll(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file) throws Exception {

		Iterable<Meeting> i = meetingService.meetList();
		System.out.println(i);

		return i;
	}
	
	@PostMapping("/getone")
	public MeetingDTO getOne(Long meetingId) throws Exception {	
		return meetingService.meetView(meetingId);
	}
	
	@PostMapping("/meetmeet/iswriter")
	public boolean isWriter(MeetingDTO meeting, HttpServletRequest req) throws Exception {
		if(req.getSession().getAttribute("accountId") != null) {
			return req.getSession().getAttribute("accountId").equals(meeting.getMaster_id());
		}else {
			return false;
		}
	}
	
	@GetMapping("/getmymeet")
	public ModelAndView getMyMeet(HttpServletRequest req) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		
		if(req.getSession().getAttribute("accountId") != null) {
			
			String accountId = (String) req.getSession().getAttribute("accountId");
			List<MeetingParticipant> mps = meetingParticipantService.searchByParticipant(accountId);
			List<MeetingDTO> meetings = new ArrayList<>();
			
			for(MeetingParticipant mp:mps) {
				meetings.add(meetingService.meetView(mp.getMeetingId()));
			}
			
			modelAndView.addObject("meetings", meetings);
			modelAndView.setViewName("mymeeting");
			System.out.println(meetings);
			
//			return req.getSession().getAttribute("accountId").equals(meeting.getMaster_id());
			
		}else {
			modelAndView.setViewName("redirect:/tohome");
		}
		
		return modelAndView;
	}

}
