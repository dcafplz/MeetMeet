## 서비스 소개
관심사, 위치기반 모임 서비스 Meet-Meet🎉

## 서비스 기능
회원의 관심사를🥳 저장하여 관심사와 일치하는 모임만 볼 수 있다.
회원이 저장한 위치에서 모임 장소로 이동하는  🚌대중교통 길찾기 기능을 제공한다.

## 구현 예시 및 코드

### DB
```sql
ALTER TABLE place ADD FOREIGN KEY (account_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE friend_list ADD FOREIGN KEY (id1) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE friend_list ADD FOREIGN KEY (id2) REFERENCES account  (account_id) on delete cascade;
alter table friend_list add constraint ck_friendList check (id1 != id2);
ALTER TABLE friend_request ADD FOREIGN KEY (request_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE friend_request ADD FOREIGN KEY (requested_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE preference ADD FOREIGN KEY (account_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE meeting ADD FOREIGN KEY (master_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE meeting_participant ADD FOREIGN KEY (participant_id) REFERENCES account  (account_id) on delete cascade;
ALTER TABLE meeting_participant ADD FOREIGN KEY (meeting_id) REFERENCES meeting  (meeting_id) on delete cascade;

alter table friend_list add unique(id1, id2);
alter table friend_request add unique(request_id, requested_id);

	
	
}
```

### Back-End
```java
package meetmeet.model.dao;



import java.util.List;

import org.springframework.data.repository.CrudRepository;

import meetmeet.model.entity.Account;


public interface AccountRepository extends CrudRepository<Account, String>{

	public List<Account> findByNickNameContaining(String searching);

	
	
}

```

---- 최영준 ----
#### - Meeting CRUD
(1) MeetingRepository
``` java
@Repository
public interface MeetingRepository extends CrudRepository<Meeting, Long>{}
```
CrudRepository의 기본기능을 사용하였습니다.
&nbsp;
&nbsp;
&nbsp;

(2) MeetingService
``` java
@Service 
public class MeetingService {
	
	@Autowired
	private MeetingRepository meetingRepository;
	
	private ModelMapper modelMapper = new ModelMapper(); // 추후 빈에 등록 필요
	
	public Long meetCreate(MeetingDTO meeting, MultipartFile file) throws Exception{       

        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files";
        
        UUID uuid = UUID.randomUUID();  // random으로 식별자 이름 생

        String fileName = "meeting" + uuid + "_" + file.getOriginalFilename(); // 기존 file 이름 +
        
        // File 생성. 해당 경로에 name으로 담기는 file
        File saveFile = new File(projectPath, fileName);
        
        // File 저장. 위 throws Exception 안하면 exception 대비하라고 경고떠서 추가했음
        file.transferTo(saveFile);
        meeting.setFilename(fileName);
        meeting.setFilepath("/files/" + fileName);
        System.out.println("service : " + meeting);
        System.out.println(modelMapper.map(meeting, Meeting.class));
        Long id = meetingRepository.save(modelMapper.map(meeting, Meeting.class)).getMeetingId();
        return id;
    }
	
    public MeetingDTO meetView(Long id){
        return modelMapper.map(meetingRepository.findById(id).get(), MeetingDTO.class);
    }
    
    public void meetDelete(Long id){
    	meetingRepository.deleteById(id);
    }
    
    public Iterable<Meeting> meetList() {
    	Iterable<Meeting> p = meetingRepository.findAll();
    	p.forEach(e -> modelMapper.map(e, MeetingDTO.class));
    	return p;
    }

}
```
- MeetingService에서는 4가지 기능을 구현하였습니다.
1. Long meetCreate meetCreate(MeetingDTO meeting, MultipartFile file)
    (1) MultipartFile를 통해 모임을 만들때 필요한 사진 file을 전달 받았습니다.
    (2) 이미지 이름이 중복되지 않게 UUID library를 이용하여 random 식별자를 이용해 file명을 rename한 뒤, 저장될 경로와 함께 DTO에 저장하였습니다.
    (3) 이후, db에 저장 후 만들어진 meetingId를 반환해줍니다.
2. Iterable<Meeting> meetList()
  	(1) repository의 findAll()을 이용해 모든 search 값을 Iterable<Meeting>에 저장해주었습니다.
  	(2) forEach 함수를 이용해 각각의 객체마다 modelMapper를 통해 DTO class로 변환한 뒤, return해주었습니다.

3. MeetingDTO meetView(Long id)
4. void meetDelete(Long id)
    
&nbsp;
&nbsp;
&nbsp;
(3) MeetingController
``` java
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
	public ModelAndView meetDelete(Long meetingId, HttpServletRequest req) {
		ModelAndView modelAndView = new ModelAndView();
		meetingService.meetDelete(meetingId);
		modelAndView.setViewName("redirect:/tohome");
		
		meetingParticipantService.
		meetParticipationDelete(meetingId,
				(String) req.getSession().getAttribute("accountId"));
		
		return modelAndView;
	}
	
	@GetMapping("/meetmeet/getall")
	public Iterable<Meeting> getAll(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file) throws Exception {

		Iterable<Meeting> i = meetingService.meetList();
		System.out.println(i);

		return i;
	}
	
	@PostMapping("/meetmeet/getone")
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
	
	@GetMapping("/meetmeet/getmymeet")
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
			
		}else {
			modelAndView.setViewName("redirect:/tohome");
		}
		
		return modelAndView;
	}

}
```
- MeetingController에서는 9가지 기능을 구현하였습니다.
1. ModelAndView meetCreate(MeetingDTO meeting, Model model, MultipartFile file, HttpServletRequest req)
    (1) req.getSession()을 통해, 로그인한 회원의 시도가 아니라면 home으로 redirect 되게 처리하였습니다.
    (2) 로그인한 회원이라면, MeetingDTO에 작성자(MasterId)를 추가해주어 service에 file과 함께 전달하여 새로 생긴 meeting id값을 반환 받습니다.
    (3) meeting을 만든 회원도 해당 Meeting에 참가하도록, 새로운 MeetingParticipantDTO 객체를 만들어서 meet_participate db에 저장해주었습니다.
    (4) 이후, 신규로 만든 meeting 정보와 해당 meeting의 상세 페이지로 redirect 해주었습니다.

3. ModelAndView meetUpdate(@PathVariable("meetingId") Long meetingId, MeetingDTO meeting, Model model, MultipartFile file)
4. ModelAndView meetDelete(Long meetingId, HttpServletRequest req)
5. boolean isWriter(MeetingDTO meeting, HttpServletRequest req)
6. ModelAndView getMyMeet(HttpServletRequest req)
7. ModelAndView meetView(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file)
8. Iterable<Meeting> getAll(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file)
9. MeetingDTO getOne(Long meetingId)



### Front-End
프론트 내용
```html
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<footer>
	<div class="row gx-4 gx-lg-5 justify-content-center">
		<div class="col-lg-4 text-center mb-5 mb-lg-0">
			<i class="bi-phone fs-2 mb-3 text-muted"></i>
			<div>+82 (02)123-4567</div>
		</div>
	</div>
	<div class="container px-4 px-lg-5">
		<div class="small text-center text-muted">Copyright &copy; 2022
			- MeetMeet</div>
	</div>
</footer>
}
```
## 참여자
- 신동혁 https://github.com/SHINDongHyeo
- 임주완 https://github.com/dcafplz
- 최영준 https://github.com/Choi-Korean
