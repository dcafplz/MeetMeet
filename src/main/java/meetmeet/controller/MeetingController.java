package meetmeet.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
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
import meetmeet.model.entity.Account;
import meetmeet.model.entity.Meeting;
import meetmeet.service.MeetingService;

@RestController
public class MeetingController {

	@Autowired
	private MeetingService meetingService;

	@PostMapping("/create-meet")
	public ModelAndView meetCreatePage(Model model, HttpSession session) throws Exception {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("createmeet.html");
		
		return modelAndView;
	}
	
	@PostMapping("/meetmeet/create-meet")
	public ModelAndView meetCreate(MeetingDTO meeting, Model model, MultipartFile file, HttpServletRequest req) throws Exception {
//		System.out.println(req.getSession().getAttribute("account"));
		meeting.setMaster_id(((AccountDTO) (req.getSession().getAttribute("account"))).getAccountId());
		ModelAndView modelAndView = new ModelAndView();
		Long id = meetingService.meetCreate(meeting, file);
		modelAndView.setViewName("redirect:../meetmeet/detail?meetingId=" + id);
		modelAndView.addObject("meeting", meetingService.meetView(id));

		return modelAndView;
	}

	@GetMapping("/meetmeet/detail")
	public ModelAndView meetView(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file) throws Exception {
		System.out.println(meetingId);
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
	public ModelAndView meetDelete(Long meetingId) {
		ModelAndView modelAndView = new ModelAndView();
		meetingService.meetDelete(meetingId);
		modelAndView.setViewName("redirect:../home.html");
		return modelAndView;
	}
	
	@GetMapping("/getall")
	public Iterable<Meeting> getAll(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file) throws Exception {

		Iterable<Meeting> i = meetingService.meetList();
		System.out.println(i);

		return i;
	}

}
