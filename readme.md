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
