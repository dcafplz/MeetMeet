package meetmeet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class AccountDTO {
	
	private String accountId;
	private	String pw;
	private String hashSalt;
	private String pwQuestion;
	private String nickName;
}
