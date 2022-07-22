# 서비스 소개
관심사, 위치기반 모임 서비스 Meet-Meet🎉

# 서비스 기능
회원의 관심사를 저장하여 관심사와 일치하는 모임만 볼 수 있다.    
회원이 저장한 위치에서 모임 장소로 이동하는  🚌대중교통 길찾기 기능을 제공한다.

# DB 💾
## DB 설계  
![DB Erd](https://user-images.githubusercontent.com/105038597/180125178-a5506f9a-99eb-47f2-b0a2-5799e9b746bc.JPG)    
계정정보를 담는 Account, 모임 정보를 담는 meeting을 중심으로 다수의 테이블을 생성    
Account table에 모두 외래키로 연결 / ON DELETE CASECADE속성, UNIQUE속성을 활용해 무결성 유지
```sql
ALTER TABLE friend_list ADD FOREIGN KEY (id1) REFERENCES
account  (account_id) on delete cascade;

# 멀티 컬럼 유니크
ALTER TABLE friend_list ADD UNIQUE(id1, id2);
```
# 사용자 계정 기능🥳

### Front-End
DB 오류를 최소화 하고, 사용자가 즉각적으로 입력해야하는 값들을 체크할 수 있게 required, disabled 활용   
onchange 이벤트와 js 함수를 이용해 사용자 입력을 검증 

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
### Back-End
#### 1. hash 함수 및 랜덤 생성된 hash_salt를 통해 암호화하여 pw 저장 :lock:
사용자에게 입력받은 정보로 DB저장 메소드
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
암호화 관련 클래스 
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
#### 2. 로그인, 로그인 상태 유지 및 로그아웃 기능    
사용자 입력값과 DB저장 값 비교 일치시 사용자 정보 세션에 저장 
```java
@PostMapping("login")
public String login(String accountId, String pw, HttpSession session) throws NoSuchAlgorithmException {

	Optional<Account> account = accountService.findById(accountId);
	try {
		if (PwSecurityService.checkPw(account.get(), pw)) {
			session.setAttribute("accountId", account.get().getAccountId());
			session.setAttribute("nickName", account.get().getNickName());
			return "redirect:/tohome";
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "redirect:/tologin";
}
```
사용자 정보가 필요할시 프론트에 세션정보 전달
```JAVA
@ResponseBody
@PostMapping("/getsession")
public String[] getSession(HttpSession session) {
	return new String[] { session.getAttribute("accountId").toString(),
			session.getAttribute("nickName").toString() };
}
```
&nbsp;
# 🏠Home화면 기능
##### (1) 구현 화면

&nbsp;
##### (2) Front-End
1. Infinite Scroll 기능
```javascript
//스크롤 바닥 감지
window.onscroll = function(e) {
  //추가되는 임시 콘텐츠
  //window height + window scrollY 값이 document height보다 클 경우,
  if((window.innerHeight + window.scrollY) >= document.body.offsetHeight) {
    //실행할 로직 (콘텐츠 추가)
    count += 8;
    get_new_item(count - 8, count);
  }
};

get_new_item(0, 12);

// item list 추가 함수
function get_new_item(start, end){
  for(start; start < end; start ++){
    var new_id = item_list[start].meetingId	/* "btn-modal" +   */
    document.getElementById("item_list").innerHTML += 
      '<div id="' + new_id + '"class="cards" style="..."><div class="card h-100"><img class="card-img-top" src=' +
      item_list[start].img +
     ...."
  }
}
```
저희는 모임 list를 무한 스크롤 방식으로 구현했습니다. 초기에는 12개까지의 만 확인 가능하고, 스크롤과 window 높이가 document 높이보다 클 경우, 추가로 8개씩 listing 하는 방식으로 구현하였습니다.

&nbsp;
2. modal 기능
```javascript
const modal = document.getElementById("modal");

function modalOn1(e) {
  check_meetparticipate(e.target.parentElement.parentElement.parentElement.parentElement.id);
  modal.style.display = "flex";
  modal_content(e.target.parentElement.parentElement.parentElement.parentElement.id);
};

function isModalOn() {
  return modal.style.display === "flex";
};

function modalOff() {
  modal.style.display = "none";
};


const closeBtn = modal.querySelector(".close-area");

closeBtn.addEventListener("click", e => {
  modalOff();
});

modal.addEventListener("click", e => {
  const evTarget = e.target
  if(evTarget.classList.contains("modal-overlay")) {
    modalOff();
  }
});
window.addEventListener("keyup", e => {
  if(isModalOn() && e.key === "Escape") {
    modalOff();
  }
});

function modal_content(parent_id){
  if(document.getElementById("p-button") != null){
    document.getElementById("p-button").value = parent_id;
  }
  var parent_tag = document.getElementById(parent_id);
  var modal_tag = document.getElementById("modal");
  document.getElementById("detailform").action = "/meetmeet/detail?meetingId=" + parent_id;
  document.getElementById("meetingId").value = parent_id;

  for(let i = 0; i < item_list.length; i ++){
    if(item_list[i].meetingId == parent_id){
      modal_tag.getElementsByClassName("card-img-top")[0].src = item_list[i].img;
      modal_tag.getElementsByClassName("modal-name")[0].innerText = item_list[i].name;
      modal_tag.getElementsByClassName("modal-place")[0].innerText = item_list[i].place;
      modal_tag.getElementsByClassName("modal-date")[0].innerText = item_list[i].date;
      modal_tag.getElementsByClassName("modal-contents")[0].innerText = item_list[i].content;

    }
  }

}
```
메인페이지에서 간략하게 모임 정보를 더 확인할 수 있도록 모달기능을 활용하였습니다. 모임의 더보기를 클릭시 event를 modalOn 함수에 전달하여, event가 발생한 tag의 id값을 modal_content에 전달하였습니다. 이후, 기존에 비동기로 가져온 item_list에서 해당  정보를 꺼내어 모달에 추가해주는 방식으로 구현하였습니다.
모달창 바깥쪽은 modal-overlay 영역으로 구성하여, 모달 바깥을 클릭하거나 ESC를 눌러도 모달창이 닫히도록 구현하였습니다.

&nbsp;
3. 선호도 Filtering 기능
```javascript
function getPreference(){
  axios.get("account/getpreference").then(function(response) {
    item_list_temp = [];
    var user_category = response.data;
    idx_list = [];
    for(i in item_list){
      item_now = (item_list[i].category).split(',');
      result = false;
      for(j in item_now){
        for(x in user_category){
          if(item_now[j] === user_category[x]){
            result = true;
          };
        };
      };
      if(result == true){
        item_list_temp.push(item_list[i]);
      }
    }

    item_list = item_list_temp;
    document.getElementById("item_list").innerHTML = "";
    get_new_item(0, 12);
  });
}
```
filter 클릭시 메인화면의 모임 List 중, 회원이 선호하는 category에 해당하는 모임만 출력되도록 기능을 추가하였습니다. 비동기로 현재 회원의 category를 가져온 뒤, 기존에 받아온 모임 list와 비교하여 화면에 재출력하는 형식으로 구현하였습니다.

&nbsp;
##### (3) Back-End
&nbsp;
1. Controller
```java
@GetMapping("/getall")
	public Iterable<Meeting> getAll(Model model, Long meetingId, MeetingDTO meeting, MultipartFile file) throws Exception {

		Iterable<Meeting> i = meetingService.meetList();
		System.out.println(i);

		return i;
	}
```
&nbsp;
2. Service
``` java
public Iterable<Meeting> meetList() {
    	Iterable<Meeting> p = meetingRepository.findAll();
    	p.forEach(e -> modelMapper.map(e, MeetingDTO.class));
    	return p;
    }
```

# 🤝🏻 모임 만들기 기능
##### (1) 구현 화면

<img src="https://user-images.githubusercontent.com/87963586/180119155-626632b9-0e74-428c-9213-68b185f76be2.gif" width="800" height="600">

&nbsp;
##### (2) Front-End
&nbsp;
1. input data 검증
아래와 같이 pattern을 이용한 정규표현식으로 input 검증되도록 data가 db 형식에 맞는지 처리하였습니다.
``` html
<!DOCTYPE html>
...
  <div class="form-floating mb-3">
    <input class="form-control" id="title" type="text" required
           pattern="^[ㄱ-ㅎ|가-힣|0-9|\s|a-z|A-Z]+$"
           onchange="title_valid('title', 'title invalid-feedback', 'title valid-feedback')"
           name="meetingName" /> <label for="title">미팅 이름</label>
    <div class="title invalid-feedback">50자 이내로 입력해주세요.</div>
    <div class="title2 invalid-feedback">한글, 숫자, 영어만 사용 가능합니다.</div>
    <div class="title valid-feedback" style="display: none">OK</div>
...
```
위 input tag에서 change event가 발생할 때마다 아래 javascript 문을 실행하여 검증 데이터가 유효하다면, val() 함수를 이용해 현재 유효한 field가 몇개인지 체크합니다.
``` javascript
	
let nums = new Array(6);
nums.fill(0);

function title_valid(e_id, e_class_invalid, e_class_valid) {

  var e = document.getElementById(e_id);
  var ei = document.getElementsByClassName(e_class_invalid);
  var ei2 = document.getElementsByClassName("title2 invalid-feedback");
  var ev = document.getElementsByClassName(e_class_valid);

  if (e.checkValidity() && e.value.length <= 50) {
    ev[0].style.display = "block";
    ei[0].style.display = "none";
    ei2[0].style.display = "none";
    nums[4] = 1;

  } else if(e.checkValidity() && e.value.length > 50){
    ei[0].style.display = "block";
    ei2[0].style.display = "none";
    nums[4] = 0;

  } else if(!e.checkValidity() && e.value.length <= 50){
    ei[0].style.display = "none";
    ei2[0].style.display = "block";
    nums[4] = 0;

  }else {
    ev[0].style.display = "none";
    ei[0].style.display = "block";
    ei2[0].style.display = "block";
    nums[4] = 0;
  }
  val();
};

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
    document.getElementById("singupButton").disabled = false;
  }
};
```
input의 모든 필드가 유효하다면 제출버튼을 활성화시켜서 회원이 모임을 생성할 수 있도록 구현하였습니다.

&nbsp;
2. 이미지 input
&nbsp;
인코딩타입을 multipart/form-data 형식으로 전달하였습니다.

``` html
<!DOCTYPE html>
<form id="signupform" action="/meetmeet/create-meet" method="post" enctype="multipart/form-data">
<div class="form-floating mb-3">
  <input id='create-meet' type='file' name='file'
         onchange="change_image(event)">모임 사진을 올려주세요</input>
</div>
<div class="form-floating mb-3">

  <!-- 오른쪽 meeting 생성 미리보기 부분 -->
 <div class="col-lg-6">
   <div id="image-block" class="form-floating mb-3"
         style="display: none">
     <img id="image-block-image" class="card-img-top" src="" alt="..." />
   </div>
```
``` javascript
/* image 미리보기 기능  */
const reader = new FileReader();

reader.onload = (readerEvent) => {
  console.log(document.querySelector("#image-block-image"));
  document.querySelector("#image-block-image").setAttribute("src", readerEvent.target.result);
  //파일을 읽는 이벤트가 발생하면 img_section의 src 속성을 readerEvent의 결과물로 대체함
};

function change_image(e) {
  console.log(e);
  document.getElementById("image-block").style.display = "block";
  const imgFile = e.target.files[0];
  console.log(imgFile);
  reader.readAsDataURL(imgFile);
  console.log(reader);

  //업로드한 이미지의 URL을 reader에 등록
}
```
이미지가 uploda되면 change_image 함수가 실행되어 imgae-block에 미리보기 형식으로 이미지를 출력하도록 구현하였습니다.

&nbsp;
3. 주소 입력
&nbsp;
주소입력은 Naver Map API Geocoding과 Reverse Geocoding을 이용하였습니다. 
map이나 address input칸에 click event가 발생하면 해당 주소의 lat, lng 정보가 자동으로 hidden input 태그에 기입되게 구현하였습니다.
``` html
<div class="form-floating mb-3">
  <input class="form-control" id="address" type="text"
         placeholder="검색할 주소" value="" onchange="initGeocoder()">
  <label for="address">미팅 주소</label>
  <input type="hidden" id="lat" value="" name="meetingPlaceLat"> <input type="hidden" id="lng" value="" name="meetingPlaceLng">
</div>
```
``` javascript
function searchCoordinateToAddress(latlng) {
 ...
}

function searchAddressToCoordinate(arr) {
 ...
}

function initGeocoder() {
  ...
}

naver.maps.onJSContentLoaded = initGeocoder;
```
&nbsp;
##### (3) Back-End

1. MeetingService
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
2. MeetingController
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

---
&nbsp;
# 🔍모임 상세페이지

##### (1) 구현 화면

<img src="https://user-images.githubusercontent.com/87963586/180118954-d40208d0-97dc-4267-885a-4b0367685541.gif" width="800" height="600">


&nbsp;
##### (2) Front-End
&nbsp;
1. 모임 data 출력
```html
<div class="modal-contents" style="width:588px; height: 100px; text-align: center;" th:text="${meeting.meetingDetail} "></div>
```
Server에서 받아온 모임 data는 thymeleaf를 이용하여 출력하였습니다.
&nbsp;
2. 모임 수정/삭제/참여/취소 session 처리
```html
<div th:if="${session.accountId != null}">
  <a id="button-participate" class="btn btn-primary btn-xl"  style="display: none" onclick="meetparticipate()">참여하기</a>
  <a id="button-participate-cancel" class="btn btn-primary btn-xl" style="display: none" onclick="meetparticipatecancel()">취소하기</a>
  <a id="button-delete" class="btn btn-primary btn-xl" style="display: none" th:href="@{/meetmeet/delete(meetingId=${meeting.meetingId})}">모임삭제</a>
  <br>
  <a id="button-modify" class="btn btn-primary btn-xl" style="display: none; opacity:0.8" th:href="@{/meetmeet/modify/{meetingId}(meetingId=${meeting.meetingId})}">모임수정</a>
</div>
```
마찬가지로 thymeleaf 문법을 이용하여, session에 account id가 존재하는지 확인하여 알맞은 button들을 보여주도록 구현하였습니다.
```javascript
/* writer가 맞는지 체크*/
	isWriter();
	function isWriter(){
		axios.post('/meetmeet/iswriter', null, {params:{
			master_id: master_id
		}
		})
		.then(function (response) {	// 정상 응답시에 자동 호출
			if(response.data){
				isWriterTrue();
			}else{
				check_meetparticipate();
			};
		})
			.catch(function (error) {	// 응답에러 발생시 자동 호출
			console.log(error);
		});
	}
```
javascript에서 비동기 형식으로 back-end에 요청하여 현재 회원이 글 작성자가 맞는지 확인 후, 글 작성자가 맞다면 수정/삭제 버튼을, 아니라면 참가/취소 버튼이 보이도록 구현하였습니다.
모임 참여/취소는 아래와 같이 비동기로 처리하였습니다.
```javascript
/* meeting 참가하기 */
	 function meetparticipate(){
		axios.post('/meetparticipate', null, {params:{
			meetingId: meeting_id
		}
		})
		.then(function (response) {	
		 	if(response.data){
				isWriterTrue();
			}else{
				check_meetparticipate();
			};
		})
			.catch(function (error) {	
			console.log(error);
		});
	}
	
	 /* meeting 취소하기 */
		function meetparticipatecancel(){
			axios.post('/cancel/meetparticipate', null, {params:{
				meetingId: meeting_id
			}
			})
			.then(function (response) {	
				console.log(response);
			 	if(response.data){
			 		isWriterTrueParticipateFalse();
				}
			})
				.catch(function (error) {	
				console.log(error);
			});
		}
	
	/* meeting 참가중인지 여부*/
	
	function check_meetparticipate(){
		axios.post('/check/meetparticipate', null, {params:{
			meetingId: meeting_id
		}
		})
		.then(function (response) {
		 	if(response.data){
		 		isWriterTrueParticipateTrue();
			}else{
				isWriterTrueParticipateFalse();
			}
		 	// setTimeout(check_meetparticipate,2000);
		})
			.catch(function (error) {
			console.log(error);
		});
	}
```
&nbsp;
3. 대중교통 길찾기
페이지 로딩시 회원정보 DB에 저장된 위치를 출발지, 모임의 위치를 도착지로 하여 대중교통 길찾기가 실행되도록 구현하였습니다.
```javascript
/* backend에서 받아온 기초값 저장 */
/*<![CDATA[*/
let lat = /*[[ ${meeting.meetingPlaceLat} ]]*/;
let lng = /*[[ ${meeting.meetingPlaceLng} ]]*/;
let session_accountId = /*[[ ${session.accountId} ]]*/;
/*]]*/

if(session_accountId != null){
  get_direct_user();
}

function get_direct_user(){
  axios.post('/place/getone', null, {params:{
    id: session_accountId
  }
                                    })
    .then(function (response) {
    mark_direct_user(response.data);
  })
    .catch(function (error) {
    console.log(error);
  });
}

function mark_direct_user(res){		
  searchCoordinateToAddress({
    y : res.lng,
    _lat : lat,
    x : res.lat,
    _lng : lng
  })
}

function 오디세이대중교통길찾기함수(){};
```
현재 모임의 위치와 회원의 위치를 비동기로 backend에서 받아온 뒤, ODsay 대중교통 길찾기 함수를 이용하여 지도에 출력하였습니다.


##### (3) Back-End
&nbsp;
1. Controller
``` java
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
			
		}else {
			modelAndView.setViewName("redirect:/tohome");
		}
		
		return modelAndView;
	}
```


# 👬친구기능
## 1. 친구신청
- Front-End
1. '친구추가'페이지에서 닉네임으로 유저들 검색

```javascript
var friendsNum = 0;
var perPage = 12;
var pageNum = 0;
var tempData = 0;
var div = 0;
///// 1. 친구를 검색하는 함수 생성 //////
function findFriend() {
	const findUser = document.getElementById("findUser").value;
	const xhttp = new XMLHttpRequest();
    /////  3.받아온 정보로 화면 구성  ////////
	xhttp.onload = function() {   
		let res_data = this.responseText;
		let data = JSON.parse(res_data);

                // 화면 구성 생략 //
									
		}
    /////  2.서버에서 원하는 정보 비동기로 요청  ////////
	xhttp.open("get", `searchUser?searching=${findUser}&id=${User}`, true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
	xhttp.send();
}
</script>
```


2. 검색된 유저들 중 원하는 인원에 해당되는 친구신청 버튼 클릭

```javascript
/// 1.친구신청하는 함수 생성   ///
function addFriend(v) {
	const findUser = v;
	const xhttp = new XMLHttpRequest();
    /// 3.받아온 정보를 알람으로 출력 ////
	xhttp.onload = function() {
		let res_data = this.responseText;
		alert(res_data);
	}
    /// 2.서버에 정보 비동기로 넘겨주고 결과요청  ///
	xhttp.open("post", `friendlist/post?id1=${User}&id2=${findUser}`, true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
	xhttp.send();
}
```


- Back-End
1. AccountRepository에서 해당 조건을 만족하는 Account리스트 반환

```java
public interface AccountRepository extends CrudRepository<Account, String>{
	public List<Account> findByNickNameContainingAndAccountIdNot(String searching,String id);
}
```

2. 여러 조건을 검증한 뒤 친구요청목록에 저장

```java
// 1.닉네임으로 해당 Account 검색
Optional<Account> result1 = accountRepository.findById(id1);
Optional<Account> result2 = accountRepository.findById(id2);


// 2.검색된 Account로 친구목록에 존재하는지 검사
@Query("SELECT fl FROM FriendList fl WHERE fl.id1.accountId=:id1 AND fl.id2.accountId=:id2")
	public abstract Optional<FriendList> findMyFunction(String id1, String id2);

// 3.모든 검사를 마치면 친구요청목록에 저장
friendRequestRepository.save(modelMapper.map(result, FriendRequest.class));

```

## 2.친구신청 수락/삭제
- Front-End
1. '친구요청'페이지에서 친구요청들 확인

```javascript
// 1.친구요청 목록을 보여주는 함수 생성 //
function showRequest(){
	const xhttp = new XMLHttpRequest();
    // 3. 받아온 정보로 화면구성 //
	xhttp.onload = function() {
		let res_data = this.responseText;
		let data = JSON.parse(res_data);
		
		// 화면 구성 생략 //
	}
    // 2.서버에 정보 비동기로 요청 ///
	xhttp.open("get", `friendrequest/findFriendRequestByRequestedId?requestedId=${User}`, true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
	xhttp.send();
}
```

2. 친구 수락/삭제 버튼을 통해 원하는 작업 진행

```javascript
function acceptRequest(v1,v2,v3){	
	const request1 = v1;
	const request2 = v2;
	const request3 = v3;
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		let res_data = this.responseText;
		alert(res_data);
		showRequest();
	}
	xhttp.open("delete", `friendrequest/accept?id=${request1}&id1=${request2}&id2=${request3}`, true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
	xhttp.send();
}
function deleteRequest(v){	
	const request = v;
	const xhttp = new XMLHttpRequest();
	xhttp.onload = function() {
		let res_data = this.responseText;
		alert(res_data);
		showRequest();
	}
	xhttp.open("delete", `friendrequest/delete?id=${request}`, true);
	xhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded;charset=UTF-8");
	xhttp.send();
}
```

- Back-End
1. 수락

```java
    Optional<Account> result1 = accountRepository.findById(id1);
    Optional<Account> result2 = accountRepository.findById(id2);
    AccountDTO result3 = null;
    AccountDTO result4 = null;
    if (result1.isPresent()) {
        result3 = modelMapper.map(result1.get(), AccountDTO.class);
    }
    if (result2.isPresent()) {
        result4 = modelMapper.map(result2.get(), AccountDTO.class);
    }
    FriendListDTO result = FriendListDTO.builder().id1(result3).id2(result4).build();
    friendListRepository.save(modelMapper.map(result, FriendList.class));
    FriendListDTO resultt = FriendListDTO.builder().id2(result3).id1(result4).build();
    friendListRepository.save(modelMapper.map(resultt, FriendList.class));
```

2. 삭제

```java
friendRequestRepository.deleteById(id2);
```
## 참여자
- 신동혁 https://github.com/SHINDongHyeo
- 임주완 https://github.com/dcafplz
- 최영준 https://github.com/Choi-Korean
