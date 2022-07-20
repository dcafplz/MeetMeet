## 서비스 소개
관심사, 위치기반 모임 서비스 Meet-Meet🎉

## 서비스 기능
회원의 관심사를🥳 저장하여 관심사와 일치하는 모임만 볼 수 있다.
회원이 저장한 위치에서 모임 장소로 이동하는  🚌대중교통 길찾기 기능을 제공한다.

## 구현 예시 및 코드

### DB

DB설계도
삽입예정

외래키의 ON DELETE CASECADE속성, UNIQUE속성을 활용해 무결성 유지
```sql
ALTER TABLE friend_list ADD FOREIGN KEY (id1) REFERENCES
account  (account_id) on delete cascade;

# 멀티 컬럼 유니크
ALTER TABLE friend_list ADD UNIQUE(id1, id2);
}
```

### Back-End

hash 함수 및 랜덤 생성된 hash_salt를 통해 암호화하여 pw 저장 
```java
@PostMapping("account/signup")
public String signup(AccountDTO account) throws NoSuchAlgorithmException {
	
	//Random 객체 생성
	Random random = new Random();

	//hash_salt 값을 랜덤으로 생성해 즉시 계정정보에 저장
	account.setHashSalt(random.ints(48,123)
  			.filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
			.limit(3)
			.collect(StringBuilder::new,StringBuilder::appendCodePoint, StringBuilder::append)
			.toString());
            
	//salt값과 사용자 입력 pw값을 즉시 암호화해 pw에 set
	account.setPw(PwSecurity.hashing(account.getPw(), account.getHashSalt()));
    
    	//mapping
    	Account accountEntity = modelMapper.map(account, Account.class);
    
    	//insert, redirect
	dao.save(accountEntity);
	return "redirect:/tologin";
}
```
```java
public class PwSecurity {
	
	//사용자가 입력한 id, pw db에 저장된 hash_salt값으로 계정정보 확인
	public static boolean checkPw(Account account, String pw) throws Exception {
		
		return account.getPw().contentEquals(hashing(pw, account.getHashSalt()));

	}
	
	//사용자가 입력한 pw와 랜덤 생성된 hash_salt로 pw암호화
	public static String hashing(String pw, String salt) throws NoSuchAlgorithmException {
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		byte[] digest = md.digest((pw+salt).getBytes(StandardCharsets.UTF_8));
		String sha256 = DatatypeConverter.printHexBinary(digest).toLowerCase();
		
		return sha256;
        
		}

	}
	
}
```
### Front-End
DB 오류를 최소화 하고, 사용자가 즉각적으로 입력해야하는 값들을 체크할 수 있게 required 및 disabled를 활용

html code
```html
<!-- required의 minlength, maxlength, pattern속성 활용 입력받을 id값 규정, 값이 변할때 마다 idval()호출해 값 검증 -->
<input class="form-control" name="accountId" id="id" 
       type="text" required minlength="4" maxlength="15" 
       pattern="^[a-zA-Z0-9]+$"onchange="idval()" /> 
<label for="id">ID</label>

<!-- 규정된 id값과 다르게 입력할때 나오는 메시지 -->
<div class="id invalid-feedback">
  ID의 길이는 영문과 숫자만 사용가능하며 4자이상 15자 이하여야 합니다.
</div>

<!-- 비동기로 계정정보를 가져와 입력된 id가 이미 존재하면 반환되는 메시지 -->
<div class="idd invalid-feedback" 
     style="display: none">중복된 ID 입니다.</div>

<!-- 모든 조건을 만족했을 때 나오는 메시지 -->
<div class="id valid-feedback" 
     style="display: none">사용 가능한 ID입니다.</div>

<!-- disabled를 활용 검증된 값을 입력하지 않으면 가입 시도가 불가능 -->
<button class="btn btn-primary btn-xl" id="singupButton"
        type="submit" disabled="ture">sign up</button>
```
javascript code
```javascript
/* input태그 개수만큼 생성된 배열 */
let nums = new Array(6);
nums.fill(0);

/* id값 검증 함수  */
function idval() {

  var e = document.getElementById("id");
  var ei = document.getElementsByClassName('id invalid-feedback');
  var ei2 = document.getElementsByClassName('idd invalid-feedback');
  var ev = document.getElementsByClassName('id valid-feedback');
          
/* 비동기를 통한 중복아이디 체크 및 Validity 체크*/
  axios.get("account/check", {
    params : {
      accountId : e.value
    }
  }).then(function(response) {
    
    /*id값의 Validity를 체크해 메시지 표시 여부 설정 */ 
    if (e.checkValidity()) {
      ev[0].style.display = "block";
      ei[0].style.display = "none";
      
      /* 중복되는 id 여부를 체크해 메시지 표시 여부 설정 */ 
      if (response.data == "Y") {
        ev[0].style.display = "none";
        ei2[0].style.display = "block";
        nums[0] = 0;
      } else {
        ei2[0].style.display = "none";
       	/* 모든 조건 만족, 1입력 */
        nums[0] = 1;
      }
    } else {
      ev[0].style.display = "none";
      ei[0].style.display = "block";
      nums[0] = 0;
    }
    ;
    /* val 호출 */
    val();
  });

  /* 회원가입의 모든 값들이 정상값이 입력되어있는지 체크*/
  function val() {
    var k = 1;
    for (i = 0; i < 6; i++) {
      if (nums[i] == 0) {
        k = 0;
        break;
      }
    }
    if (k == 0) {
      document.getElementById("singupButton").disabled = true;
    } else {
      /* 모든 input이 정상값이 입력되어 회원가입 버튼 활성화 */
      document.getElementById("singupButton").disabled = false;
    }
    ;
  };          

};
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

- MeetingService에서는 4가지 기능을 구현하였습니다.
1. Long meetCreate meetCreate(MeetingDTO meeting, MultipartFile file)
``` java

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
```
    (1) MultipartFile를 통해 모임을 만들때 필요한 사진 file을 전달 받았습니다.
    (2) 이미지 이름이 중복되지 않게 UUID library를 이용하여 random 식별자를 이용해 file명을 rename한 뒤, 저장될 경로와 함께 DTO에 저장하였습니다.
    (3) 이후, db에 저장 후 만들어진 meetingId를 반환해줍니다.
 &nbsp;
2. Iterable<Meeting> meetList()
  ``` java    
    public Iterable<Meeting> meetList() {
    	Iterable<Meeting> p = meetingRepository.findAll();
    	p.forEach(e -> modelMapper.map(e, MeetingDTO.class));
    	return p;
    }
```
    (1) repository의 findAll()을 이용해 모든 search 값을 Iterable<Meeting>에 저장해주었습니다.
    (2) forEach 함수를 이용해 각각의 객체마다 modelMapper를 통해 DTO class로 변환한 뒤, return해주었습니다.
  &nbsp;
3. MeetingDTO meetView(Long id)
4. void meetDelete(Long id)
    
&nbsp;
&nbsp;
&nbsp;
(3) MeetingController
- MeetingController에서는 8가지 기능을 구현하였습니다.
1. ModelAndView meetCreate(MeetingDTO meeting, Model model, MultipartFile file, HttpServletRequest req)
``` java
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
```
    (1) req.getSession()을 통해, 로그인한 회원의 시도가 아니라면 home으로 redirect 되게 처리하였습니다.
    (2) 로그인한 회원이라면, MeetingDTO에 작성자(MasterId)를 추가해주어 service에 file과 함께 전달하여 새로 생긴 meeting id값을 반환 받습니다.
    (3) meeting을 만든 회원도 해당 Meeting에 참가하도록, 새로운 MeetingParticipantDTO 객체를 만들어서 meet_participate db에 저장해주었습니다.
    (4) 이후, 신규로 만든 meeting 정보와 해당 meeting의 상세 페이지로 redirect 해주었습니다.
&nbsp;
2. ModelAndView meetUpdate(@PathVariable("meetingId") Long meetingId, MeetingDTO meeting, Model model, MultipartFile file)
``` java
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
```
&nbsp;
3. ModelAndView meetDelete(Long meetingId, HttpServletRequest req)
``` java
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
```
&nbsp;
4. boolean isWriter(MeetingDTO meeting, HttpServletRequest req)
``` java
	@PostMapping("/meetmeet/iswriter")
	public boolean isWriter(MeetingDTO meeting, HttpServletRequest req) throws Exception {
		if(req.getSession().getAttribute("accountId") != null) {
			return req.getSession().getAttribute("accountId").equals(meeting.getMaster_id());
		}else {
			return false;
		}
	}
```
&nbsp;
5. ModelAndView getMyMeet(HttpServletRequest req)
``` java
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
```
6. ModelAndView meetView(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file)
``` java
	@GetMapping("/meetmeet/detail")
	public ModelAndView meetView(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file) throws Exception {
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("meetdetail.html");
		modelAndView.addObject("meeting", meetingService.meetView(meetingId));

		return modelAndView;
	}
```
7. Iterable<Meeting> getAll(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file)
``` java
	@GetMapping("/meetmeet/getall")
	public Iterable<Meeting> getAll(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file) throws Exception {

		Iterable<Meeting> i = meetingService.meetList();
		System.out.println(i);

		return i;
	}
	
```
8. MeetingDTO getOne(Long meetingId)
``` java
	@PostMapping("/meetmeet/getone")
	public MeetingDTO getOne(Long meetingId) throws Exception {	
		return meetingService.meetView(meetingId);
	}
```



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
